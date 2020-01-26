import akka.actor.{Actor, ActorSystem, Props}


object Remoting2 extends App {
  val system = ActorSystem("remoting-sys")

  //val greeter2 = system.actorOf(Props[GreetingActor], "greeter-2")
  var greeter1 = system.actorSelection("akka://remoting-sys@127.0.0.1:25520/user/greeter-1")

  //val path = "akka://remoting-sys@127.0.0.1:25520"

  //val remoteActor = system.actorOf()

  greeter1 ! "anything"
}
