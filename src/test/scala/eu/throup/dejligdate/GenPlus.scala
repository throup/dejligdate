package eu.throup.dejligdate

import eu.throup.dejligdate.domain.Date
import org.scalacheck.Gen
import org.scalacheck.ops.time.ImplicitJavaTimeGenerators.arbLocalDate

import java.time.LocalDate

trait GenPlus {
  def gen: Gen.type

  val ceYear: Gen[Int]  = gen.chooseNum(1, 9999)
  val ceMonth: Gen[Int] = Gen.chooseNum(1, 12)
  val ceDay: Gen[Int]   = Gen.chooseNum(1, 31)

  val ceTriple: Gen[(Int, Int, Int)] = for {
    year  <- ceYear
    month <- ceMonth
    day   <- ceDay
    if day <= Date.daysInMonth(year, month)
  } yield (year, month, day)

  val ceDateString: Gen[String] = for {
    (y, m, d) <- ceTriple
  } yield "%04d-%02d-%02d".format(y, m, d)

  val ceDate: Gen[Date] = for {
    (y, m, d) <- ceTriple
  } yield new Date(y, m, d)

  val ceLocalDate: Gen[LocalDate] = for {
    date: LocalDate <- arbLocalDate.arbitrary
    if date.getYear > 0
  } yield date

  def unique[A](n: Int, gen: Gen[A]): Gen[Seq[A]] = for {
    set <- Gen.containerOfN[Set, A](n, gen)
    if set.size >= n
  } yield set.toSeq.take(n)

  def unique2[A](gen: Gen[A]): Gen[(A, A)] = for {
    s <- unique(2, gen)
  } yield (s(0), s(1))

  def unique3[A](gen: Gen[A]): Gen[(A, A, A)] = for {
    s <- unique(3, gen)
  } yield (s(0), s(1), s(2))
}

object GenPlus {
  implicit class Wrapper(val gen: Gen.type) extends GenPlus
}
