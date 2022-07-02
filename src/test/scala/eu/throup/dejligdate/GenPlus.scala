package eu.throup.dejligdate

import eu.throup.dejligdate.spike.Date
import org.scalacheck.Gen
import org.scalacheck.ops.time.ImplicitJavaTimeGenerators.arbLocalDate

import java.time.LocalDate

trait GenPlus {
  def gen: Gen.type

  val ceYear: Gen[Int]  = gen.chooseNum(1, 9999)
  val ceMonth: Gen[Int] = Gen.chooseNum(1, 12)
  val ceDay: Gen[Int]   = Gen.chooseNum(1, 31)

  val ceTriple: Gen[(Int, Int, Int)] = {
    for {
      year  <- ceYear
      month <- ceMonth
      day   <- ceDay
      if day <= Date.daysInMonth(year, month)
    } yield (year, month, day)
  }

  val ceDate: Gen[Date] = for {
    (y, m, d) <- ceTriple
  } yield new Date(y, m, d)

  val ceLocalDate: Gen[LocalDate] = for {
    date: LocalDate <- arbLocalDate.arbitrary
    if date.getYear > 0
  } yield date
}

object GenPlus {
  implicit class Wrapper(val gen: Gen.type) extends GenPlus
}
