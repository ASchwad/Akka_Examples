class Test(var x: Int = 4, private val y: Int = 5, z: Int){
  protected var s : String = _
  println(s"x=$x, y=$y, z=$z")
  def this(s: String) = {
    this(s.length, 8, 9)
    this.s = s
  }
}

val t1 = new Test(z=4)

var t2 = new Test(s = "Hi")

class LazyTest {
  var x ={
    println("Test for x")
    1
  }
  lazy val y ={
    println("Test for y")
    2
  }
  def z ={
    println("Test for z")
    2 + y
  }
}

val eval = new LazyTest
eval.z


trait TestTrait{
  val a = 1
  var greeting = "Hello"
}

class TestClass extends TestTrait {
  override val a = 2
  greeting = "servus"
}

var x = new TestClass
x.greeting

1 :: 2 :: 3 :: Nil

List(2).::(1).::(3)