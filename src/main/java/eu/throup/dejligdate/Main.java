package eu.throup.dejligdate;

import eu.throup.dejligdate.spike.Date;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        final var dateString1 = "1981-06-06";
        final var dateString2 = "2011-11-15";

        final var date1 = Date.parse(dateString1);
        final var date2 = Date.parse(dateString2);

        System.out.println("Date (1) is: " + date1);
        System.out.println("Date (2) is: " + date2);

        final int diff = date2.daysSince(date1);

        System.out.println("Days between is: " + diff);

        final var ld1 = LocalDate.parse(dateString1);
        final var ld2 = LocalDate.parse(dateString2);

        final var com = ld1.datesUntil(ld2).count();

        System.out.println("Java says: " + com);
    }
}
