import akka.actor.SupervisorStrategy._
import akka.actor._
import akka.persistence._

import scala.concurrent.duration._

case class Deposit(amount : Int)
case class Withdraw(amount : Int)
case class DepositedEvent(amount : Int) extends RecoveryEvent
case class WithdrawEvent(amount: Int) extends RecoveryEvent
class RecoveryEvent()

class TransactionService extends PersistentActor{

  override def receive = receiveCommand

  override def persistenceId: String = "1234567"

  var balance = 0

  val updateBalance : RecoveryEvent => Unit = {
    case DepositedEvent(amount) => {
      println(s"Balance: $balance - $amount")
      balance += amount
    }
    case WithdrawEvent(amount) => balance -= amount
  }

  override def receiveCommand: Receive =  {
    case Deposit(amount) => persist(DepositedEvent(amount))(updateBalance)
    case Withdraw(amount: Int) => {
      println(s"Withdraw $amount from Bank account")
    }
  }

  override def receiveRecover: Receive = {
    case message @DepositedEvent(amount) => updateBalance(message)
    case message @WithdrawEvent(amount) => updateBalance(message)
  }
}

object BankingApp extends App{

  val system = ActorSystem("banking-system");

  val transactionService = system.actorOf(Props[TransactionService], "transaction-service");

  transactionService ! Deposit(123)
  println("Send deposit message")
  //system.terminate()
}
