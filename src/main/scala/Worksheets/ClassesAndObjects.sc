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

// (Int => Boolean)  type definition
val f1:(Int => Boolean)  = (n: Int) => {
  println("f1")
  n % 2 == 0
}

val f2: (Int => Boolean) = {
  println("f2")
  _ % 2 == 0
}

def f3 = (n: Int) =>{
  println("f3")
  n%2 == 0
}

def f4(x: String) = (n: Int) => {
  println("f4")
  (n*x.length)%2 == 0
}

f1(1)
f1(1)

f2(1)
f2(1)

f3(1)
f3(1)

f2


f4("2")(4)

class Test(c: => Int){
  lazy val l = {println("John");1}
  val v = {println("Lisa");1}
  val d = {println("Tom");1}

  def doIt= {
    println(l+l+v+v+d+d+c+c)
  }
}

new Test({println("dco");1}).doIt
//-------------------------------------------------------

//Singleton Object
object Student {
  var currId = 0
  def nextId: Int ={
    currId += 1
    currId
  }
  def createStudent(name: String) = new Student(name)
}

//Companion Class
class Student(val name: String) {
  val matId = Student.nextId
}

val student1 = Student.createStudent("Sepp")
student1.name
student1.matId
val student2 = Student.createStudent("Fritz")
student2.matId
//-------------------------------------------------------
def varArgs(s: String*) = {
  var counter = 0;
  for(word <- s){
    counter += 1
  }
  counter
}
//-------------------------------------------------------
val divide = new PartialFunction[Int, Int] {
  def apply(x: Int) = 42 / x
  def isDefinedAt(x: Int) = x!= 0
}
divide.isDefinedAt(0)
divide(1)