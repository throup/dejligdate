package eu.throup.dejligdate

import eu.throup.dejligdate.GenPlus._
import eu.throup.dejligdate.spike.Date
import org.scalacheck.Gen
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

import java.time.{Duration, LocalDate}
import scala.util.{Failure, Success, Try}

/** This test suite uses the Java Time library as a reference for comparison. */
class JavaReferencePropertyTests
    extends AnyFreeSpec
    with Matchers
    with ScalaCheckPropertyChecks {

  "Reference comparisons with core Java library" - {
    "succeeds or fails equally with the Java library" in {
      forAll(Gen.ceYear, Gen.ceMonth, Gen.ceDay) {
        (year: Int, month: Int, day: Int) =>
          {
            val mDate = Try { new Date(year, month, day) }
            val jDate = Try { LocalDate.of(year, month, day) }

            (mDate, jDate) match {
              case (Success(_), Success(_)) => succeed
              case (Failure(_), Failure(_)) => succeed
              case (Success(_), Failure(_)) =>
                fail("Ours succeeded, but the java library failed.")
              case (Failure(_), Success(_)) =>
                fail("Ours failed, but the java library succeeded.")
            }
          }
      }
    }

    "can build (without Exception) from any Common Era LocalDate" in {
      forAll(Gen.ceLocalDate) { (jdate: LocalDate) =>
        {
          new Date(
            jdate.getYear,
            jdate.getMonthValue,
            jdate.getDayOfMonth
          )
        }
      }
    }

    "returns same difference as Java library" in {
      forAll(
        Gen.ceTriple,
        Gen.ceTriple
      ) { case ((y1, m1, d1), (y2, m2, d2)) =>
        val mdate1 = new Date(y1, m1, d1)
        val mdate2 = new Date(y2, m2, d2)
        val mdiff  = mdate1 daysBetween mdate2

        val jdate1 = LocalDate.of(y1, m1, d1)
        val jdate2 = LocalDate.of(y2, m2, d2)

        val duration =
          Duration.between(jdate1.atStartOfDay(), jdate2.atStartOfDay())
        val jdiff    = Math.abs(duration.toDays)

        mdiff shouldBe jdiff
      }
    }
  }
}
