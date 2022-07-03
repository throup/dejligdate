package eu.throup.dejligdate

import eu.throup.dejligdate.GenPlus._
import eu.throup.dejligdate.spike.Date
import org.scalacheck.Gen
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class DatePropertyTests
    extends AnyFreeSpec
    with Matchers
    with ScalaCheckPropertyChecks {
  "Date properties" - {
    "A single date" - {
      "should be equal to itself" in {
        forAll(Gen.ceDate) { date =>
          date shouldBe date
        }
      }

      "days between itself should be zero" in {
        forAll(Gen.ceDate) { date =>
          date.daysBetween(date) shouldBe 0
        }
      }

      "days since itself should be zero" in {
        forAll(Gen.ceDate) { date =>
          date.daysSince(date) shouldBe 0
        }
      }
    }

    "Two unique dates" - {
      "should not be equal" in {
        forAll(Gen.unique2(Gen.ceDate)) { case (date1, date2) =>
          date1 shouldNot be(date2)
        }
      }

      "days between should be non-zero" in {
        forAll(Gen.unique2(Gen.ceDate)) { case (date1, date2) =>
          date1.daysBetween(date2) shouldNot be(0)
        }
      }

      "days since should be non-zero" in {
        forAll(Gen.unique2(Gen.ceDate)) { case (date1, date2) =>
          date1.daysSince(date2) shouldNot be(0)
        }
      }

      "days between should be same, no matter the order" in {
        forAll(Gen.unique2(Gen.ceDate)) { case (date1, date2) =>
          date1.daysBetween(date2) shouldBe date2.daysBetween(date1)
        }
      }

      "days since should have opposite sign, when order reversed" in {
        forAll(Gen.unique2(Gen.ceDate)) { case (date1, date2) =>
          date1.daysSince(date2) shouldBe -date2.daysSince(date1)
        }
      }
    }

    "Three unique dates" - {
      "daysSince combine across pairs" in {
        forAll(Gen.unique3(Gen.ceDate)) { case (d1, d2, d3) =>
          val diff23 = d3.daysSince(d2)
          val diff12 = d2.daysSince(d1)
          val diff13 = d3.daysSince(d1)

          diff13 shouldBe (diff12 + diff23)
        }
      }

      "daysBetween combine across sorted pairs" in {
        forAll(Gen.unique(3, Gen.ceDate)) { dates =>
          val sorted = dates.sorted
          val d1     = sorted(0)
          val d2     = sorted(1)
          val d3     = sorted(2)

          val diff23 = d3.daysBetween(d2)
          val diff12 = d2.daysBetween(d1)
          val diff13 = d3.daysBetween(d1)

          diff13 shouldBe (diff12 + diff23)
        }
      }
    }

    "Parsing" - {
      "parsing a formatted date string should give an equal date" in {
        forAll(Gen.ceDate) { date =>
          val dateString = date.formatted()
          val parsed     = Date.parse(dateString)

          parsed shouldBe date
        }
      }

      "formatting a date, constructed from a string, should give the original string" in {
        forAll(Gen.ceDateString) { input =>
          val date   = Date.parse(input)
          val output = date.formatted()

          println(s"$input === $output")
          output shouldBe input
        }
      }
    }
  }
}
