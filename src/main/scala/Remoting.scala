import akka.actor.{Actor, ActorSystem, Props}

class GreetingActor extends Actor{
  override def receive: Receive = {
    case "something" => println("Hey")
    case _ => println("Ahoi")
  }
}

object Remoting extends App {
  val system = ActorSystem("remoting-sys")

  val greeter = system.actorOf(Props[GreetingActor], "greeter-1")
  //println(greeter.path)
  greeter ! "something"
}
