import scala.annotation.tailrec

val name = "Test"

def factorial(n: Int): Int = {
  if(n == 0) 1
  else
    n * factorial(n-1)
}


factorial(5)

def GCD(n: Int, m: Int): Int =
{
  // tail recursion function defined
  @tailrec def gcd(x:Int, y:Int): Int=
  {
    if (y == 0) x
    else gcd(y, x % y)
  }
  gcd(n, m)
}

GCD(15, 25)


def factorialTR(n: Int): Int={
  @tailrec def factorialStep(n: Int, result: Int): Int={
    if(n == 0) result
    else factorialStep(n-1, result * n)
  }
  factorialStep(n, 1)
}

@tailrec def factorialStep(n: Int, result: Int): Int={
  if(n == 0) result
  else factorialStep(n-1, result * n)
}

factorialStep(3, 1)

factorialTR(5)