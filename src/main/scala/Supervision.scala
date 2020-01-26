import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume}
import akka.actor.{Actor, ActorRef, ActorSystem, AllForOneStrategy, OneForOneStrategy, Props}

case class dontCareException(e: Any) extends Throwable
case class worstCaseException(e: Any) extends Throwable

class A extends Actor {
  override def supervisorStrategy = AllForOneStrategy() {
    case worstCaseException(e) => Restart
  }
  override def receive: Receive = {
    case "Something" => println("Something")
  }
  val x = context.actorOf(Props[B], "actor-b");
}

class B extends Actor {
  override def postRestart(reason: Throwable): Unit = {println("B: Going doooooooown.")}
  println("B created")
  val c1 = context.actorOf(Props[C], "actor-c1");
  val c2 = context.actorOf(Props[C], "actor-c2");
  override def supervisorStrategy = OneForOneStrategy() {
    case dontCareException(e) => Resume
    case worstCaseException(e) => Escalate
  }
  override def receive: Receive = {
    case "Something" => context.actorOf(Props[C], "actor-c" + scala.util.Random)
    case a => println(a)
  }
}

class C extends Actor{
  override def preStart(): Unit = {println("C: IM STARTING!")}

  override def postRestart(reason: Throwable): Unit = {println("Going doooooooown.")}
  println("C created")
  override def receive = {
    case "dontCare" => throw dontCareException("dontCare")
    case "crash" => throw worstCaseException("worstCase")
  }
}

object MyApp extends App{
  val system = ActorSystem("supervision-system")

  val supervisor = system.actorOf(Props[A], ("actor-a"))

  val b = system.actorSelection("akka://supervision-system/user/actor-a/actor-b/")

  val c = system.actorSelection("akka://supervision-system/user/actor-a/actor-b/actor-c1")

  c ! "crash"

}

