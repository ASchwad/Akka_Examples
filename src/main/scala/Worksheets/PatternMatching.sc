trait Member {
  val name : String
}

case class Student(name: String, id: Int) extends Member
case class Professor(name: String, office: String) extends Member

def check(member: Member) = member match{
  //case Professor("Franz","U131") => println("Its a prof")
  case Student(i, 1234) => println(s"Student: $i")
  case p : Professor => println(p)
  case p @ Professor("Franz",x) => println(p)
}

val s1 = new Student("Sepp", 1234)
val p1 = new Professor("Franz", "U131")

check(p1)
check(s1)

//----------------------------------------------------

val myList = List(1,2,3)
myList.getClass

def iterate(list: List[Int]) = list match {
  case 1 :: 2 :: Nil => println("YES!")
  case head :: tail => println(head + " and " + tail.length)
  case _ => println("Something else...")
}

iterate(myList)
iterate(List(1,2,3,4,5))

//----------------------------------------------------

val listOfLists = List(List(1,2,3), List(4,5,6), List(7,8))
var sum = 0
for(list <- listOfLists if(list.length>2); number <- list) yield sum += number
print(sum)

val list = List("Hello", "World")
for(e <- list) println(e)