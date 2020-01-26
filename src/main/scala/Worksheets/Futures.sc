import java.util.Calendar

import scala.concurrent._
import ExecutionContext.Implicits.global

lazy val test = {
  val f1 = Future{ Thread.sleep(600); 1 }
  val f2 = Future{ Thread.sleep(600); 2 }
  println(s"Futures started at ${Calendar.getInstance().getTime()}")
  val result: Future[(Int,Int)] = f1 zip f2
  result map { case (a,b) => a + b } foreach {
    aPlusB => println(s"Result is $aPlusB at ${Calendar.getInstance().getTime()}")
  }
}

test