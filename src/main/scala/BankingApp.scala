import akka.actor.SupervisorStrategy._
import akka.actor._
import akka.persistence._

import scala.concurrent.duration._

trait Message
trait RecoveryEvent

case class Deposit(amount : Int) extends Message
case class Withdraw(amount : Int) extends Message

case class DepositedEvent(amount : Int) extends RecoveryEvent
case class WithdrawEvent(amount: Int) extends RecoveryEvent

//https://doc.akka.io/docs/akka/2.3/scala/persistence.html
//Event-Sourcing: State changes of an actor are kept in a journal. If the actor crashes, the journal entries can be
// iterated to retrieve the current state.

//Snapshots: some actors may be prone to accumulating extremely long event logs and experiencing long recovery times,
// Snapshots can dramatically reduce recovery times of persistent actors and views

class TransactionService extends PersistentActor{

  override def receive = receiveCommand

  override def persistenceId: String = "1234567"

  var balance = 0

  val updateBalance : RecoveryEvent => Unit = {
    case DepositedEvent(amount) => {
      println(s"Balance: $balance + $amount")
      balance += amount
      println(s"New Balance: $balance")
    }
    case WithdrawEvent(amount) =>
      {
        println(s"Balance: $balance - $amount")
        balance -= amount
        println(s"New Balance: $balance")
        //saveSnapshot(amount)
      }
  }

  //Two kind of Receive commands: For commands during runtime and for recovery phase

  //Commands during runtime call persist on Recovery Events before actually updating the state.
  override def receiveCommand: Receive =  {
    case Deposit(amount) => {
      persist(DepositedEvent(amount))(updateBalance)
    }
    case Withdraw(amount: Int) => {
      persist(WithdrawEvent(amount))(updateBalance)
    }
  }

  override def receiveRecover: Receive = {
    case message : DepositedEvent => updateBalance(message)
    case message : WithdrawEvent => updateBalance(message)
  }
}

object BankingApp extends App{

  val system = ActorSystem("banking-system");


  val transactionService = system.actorOf(Props[TransactionService], "transaction-service");


  transactionService ! Deposit(123)
  transactionService ! Withdraw(50)
  //system.terminate()
}
