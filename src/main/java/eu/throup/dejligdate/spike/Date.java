package eu.throup.dejligdate.spike;

import eu.throup.dejligdate.exception.DateException;
import eu.throup.dejligdate.exception.InvalidDayException;
import eu.throup.dejligdate.exception.InvalidMonthException;
import eu.throup.dejligdate.exception.InvalidYearException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

// Handle dates in the Common Era (ie from year 1 onwards)
// applying rules from the Gregorian calendar.
public class Date {
    public final int year;
    public final int month;
    public final int day;

    public static final Date ERA = new Date(1, 1, 1);

    public Date(int year, int month, int day) {
        if (year < 1) throw new InvalidYearException(year);
        if (month < 1 || month > 12) throw new InvalidMonthException(month);
        if (day < 1 || day > daysInMonth(year, month)) throw new InvalidDayException(day);

        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static Date parse(String text) {
        Pattern pattern = Pattern.compile("(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            return new Date(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3))
            );
        } else
            throw new DateException("Unable to parse date: %s.\nValid dates have the format YYYY-MM-DD.".formatted(text));
    }

    static int daysInMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;

            case 4:
            case 6:
            case 9:
            case 11:
                return 30;

            case 2:
                if (isLeapYear(year))
                    return 29;
                else
                    return 28;

            default:
                throw new InvalidMonthException(month);
        }
    }

    static int daysInYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    static boolean isLeapYear(int year) {
        if (year % 100 == 0)
            return year % 400 == 0;
        else
            return year % 4 == 0;
    }

    @Override
    public String toString() {
        return "Date(" + formatted() + ")";
    }

    public String formatted() {
        return "%04d-%02d-%02d".formatted(year, month, day);
    }

    public int daysSinceEra() {
        final var years = IntStream.range(1, year);
        final var daysInYears = years.map(Date::daysInYear);
        final var yearDays = daysInYears.sum();

        final var months = IntStream.range(1, month);
        final var daysInMonths = months.map(m -> daysInMonth(year, m));
        final var monthDays = daysInMonths.sum();

        final var dayDays = day - 1;

        return yearDays + monthDays + dayDays;
    }

    public int daysSince(Date other) {
        return daysSinceEra() - other.daysSinceEra();
    }

    public int daysBetween(Date other) {
        return Math.abs(daysSince(other));
    }
}
