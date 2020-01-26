case class Book(title: String, authors: String*)

val books: List[Book] = List(
  Book("Structure and Interpretation of Computer Programs","Abelson, Harold", "Sussman, Gerald J."),
  Book("Principles of Compilter Design", "Aho, Alfred", "Ullman, Jeffrey"),
  Book("Progranming in Modula-2", "Wirth, Jeffrey"),
  Book("Elements of ML Programming", "Wirth, Jeffrey"),
  Book("The Java language specification", "Gosling, James", "Joy, Bill", "Steele, Guy", "Bracha, Gilad")
)

for{b<-books
    a<- b.authors
    if a startsWith("A")
    } yield b

for{b1<-books
    b2<-books if b1 != b2
    a1 <- b1.authors
    a2 <-b2.authors
            if a1 ==a2
}yield b1.title

books.flatMap{b1 =>
  books withFilter (b2 => b1!=b2) flatMap {b2 =>
    b1.authors flatMap{a1 => b2.authors}
  }}





case class Student(name: String, matId: Int, specialization: String, university: String, modules: String*)

val students : List[Student] = List(
  Student("Sepp", 12345, "Web", "OTH", "HSP", "BML", "WIS"),
  Student("Fritz", 23232, "IoT", "UR", "PG1", "DCO", "WIS"),
  Student("Hubert", 12122, "ML", "FAU", "PG1", "BML", "AWS"),
  Student("Markus", 67676, "Web", "OTH", "MOD", "HW", "AWS"),
  Student("Frido", 45444, "IoT", "UR", "MOD", "BA", "PJ"),
)

val x = (for {student <- students
    modules <- student.modules if (modules startsWith("P"))
     }yield modules).distinct

//------------------------------------------------

students.map(student => student.name.toUpperCase)
//Map extracts the desired values, returns the same type of monad (List of Lists)
students.map(student => student.modules)
//FlatMap extracts the values, but flattens them by one list level to a list of modules
students flatMap {student =>
  student.modules filter(m => m.length<3)}



for{s <- students
    m <- s.modules if m.length<3}yield m
-