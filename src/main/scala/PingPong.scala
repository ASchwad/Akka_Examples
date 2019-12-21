import akka.actor._

case object PingMessage
case object PongMessage

class Ping extends Actor{
  override def receive = {
    case PongMessage => {
      println("ping")
      sender() ! PingMessage
    }
  }
}

class Pong(ping: ActorRef) extends Actor{
  override def receive = {
    case PingMessage => {
      println("pong")
      ping ! PongMessage
    }
  }
}

object PingPong extends App {
  val system = ActorSystem("ping-pong-system");

  val ping = system.actorOf(Props[Ping], "ping");
  //val pong = system.actorOf(Props(new Pong(ping)), "pong");
  val pong = system.actorOf(Props(classOf[Pong], ping), name = "pong")
  pong ! PingMessage

}