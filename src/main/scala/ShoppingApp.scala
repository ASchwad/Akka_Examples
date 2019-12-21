import akka.actor.SupervisorStrategy._
import akka.actor._
import scala.concurrent.duration._

case object AddItem
case object DoSomething
case class CreateNewCart(a: Int)

case class InvalidCommandException(s:String) extends Exception(s){}

class ShoppingCartManager extends Actor{
  override def preStart(): Unit = println("[ShoppingCartManager] Starting...")
  override def postStop(): Unit = println("[ShoppingCartManager] Stopped")

  override def supervisorStrategy = OneForOneStrategy(
    //is 1 ok, or triggers already operation? --> 1 is already triggering operation
    //maxNrOfRetries, the child actor is stopped
    maxNrOfRetries = 1,
    //withinTimeRange = 1 second,
    loggingEnabled = false){ // curried Decider implementation
    case InvalidCommandException(e) => {
      println("[ShoppingCartManager] Restarting Actor: " + sender())
      Restart
    }
  }

  override def receive = {
    case CreateNewCart(a) => {
      println("[ShoppingCartManager] Creating Cart for user " + a.toString())
      val cart = context.actorOf(Props[ShoppingCart], "cart-"+a.toString());
      //context.actorOf[Props[ShoppingCart], "cart-123"]
      cart ! AddItem
      cart ! DoSomething

      //Thread.sleep(3000)
      //cart ! DoSomething
      //context.stop(cart)
    }
  }
}

class ShoppingCart extends Actor{
  override def preStart(): Unit = println("[ShoppingCart] Starting...")
  override def postStop(): Unit = println("[ShoppingCart] Stopped")
  override def receive = {
    case AddItem => {
      println("[ShoppingCart] Add item to cart: " + self.path)
    }
    case DoSomething => {
      println("[ShoppingCart] Received DoSomething")
      throw new InvalidCommandException("[ShoppingCart] Not eligible")
    }
  }
}

class MonitoringService(knownActors: Seq[ActorRef]) extends Actor{
  override def preStart(): Unit = println("[MonitoringService] Starting...")
  override def postStop(): Unit = println("[MonitoringService] Stopped")

  knownActors.foreach(other => {
    context.watch(other)
  })

  override def receive = {
    case Terminated(terminatedActorRef) => {
      println("[MonitoringService] Actor terminated: " + terminatedActorRef)
    }
  }
}

object ShoppingApp extends App{

  val system = ActorSystem("shopping-system");

  val cartManager = system.actorOf(Props[ShoppingCartManager], "cart-manager");

  cartManager ! CreateNewCart(123)
  val monitoringService = system.actorOf(Props(classOf[MonitoringService], Seq(cartManager)), "monitoring-service");
  system.actorSelection("/user/cart-manager/cart-123") ! AddItem
  Thread.sleep(3000)

  println("Calling system.stop(cartManager)")
  system.stop(cartManager)
  system.terminate()
}
