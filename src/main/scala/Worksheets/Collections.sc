case class Book(title: String, authors: String*)
val books: List[Book] = List(
  Book( "Structure and Interpretation of Computer Programs", "Abelson, Harold",
    "Sussman, Gerald J." ),
  Book( "Principles of Compiler Design", "Aho, Alfred", "Ullman, Jeffrey" ),
  Book( "Programming in Modula-2", "Wirth, Niklaus" ),
  Book( "Elements of ML Programming", "Ullman, Jeffrey" ),
  Book( "The Java Language Specification", "Gosling, James", "Joy, Bill",
    "Steele, Guy", "Bracha, Gilad" )
)


for {
  b <- books
  a <- b.authors if(a startsWith("A"))
} yield b.title

books.flatMap(book => book.authors.filter(b => b.startsWith("A")))




for {
  b <- books
  a <- b.authors
} yield a

books flatMap(
  x => x.authors
    withFilter(y=> y.startsWith("A"))
    map( res=> x.title))

books flatMap(
  x => x.authors
    withFilter(y=> y.startsWith("A"))
    map( res=> x.title + " lul"))

val rivers = List("Isar","Danube", "Mississippi", "Rhine", "Amazonas")

rivers.sorted

rivers.sorted.reverse

rivers.sortBy(b=> b.length)

rivers.sortBy(b=> b.length).take(2)

val cities = List("New York (US)", "Tempe (US)", "Regensburg (DE)", "Hamburg (DE)", "Vienna (AT)", "Ohio (US)", "Prague (CZ)")


import scala.collection.mutable.Map
var a = Map[String, List[String]]()

cities.foreach(city => {
  a  += city.takeRight(4) -> List()
})

cities.foreach(city=>{
  a(city.takeRight(4)) =  city:: a(city.takeRight(4))
})

a

val lengths =  List(1500, 2860, 2320, 1230, 6400)
var counter = -1
var riverTuples = lengths.map(length => {
  counter = counter + 1
  Tuple2(rivers(counter), length)
})

val imdb = Map(
  "Brad Pitt" -> List("Mr. and Mrs. Smith", "Meet joe black", "benjamin Button"),
  "Angelina Jolie" -> List("Tomb Raider", "Mr and Mrs. Smith"),
  "Anthony Hopkins" -> List("Silence of the Lambs", "Meet Joe Black", "The Human Stain", "Amistad")
)

def getActorOfFilm(movies: Map[String, List[String]], film: String){
  println(film)
  movies.values.flatMap(res => res)
}
println(getActorOfFilm(imdb, "benjamin Button"))

def getMovieOfActor(movies: Map[String, List[String]], actor: String){
  movies.keys.map( a =>
  a withFilter(actor))
}
getMovieOfActor(imdb, "Angelina Jolie")