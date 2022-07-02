package eu.throup.dejligdate.cli;

import eu.throup.dejligdate.spike.Date;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.time.LocalDate;
import java.util.concurrent.Callable;

@Command(name = "dejligdate", mixinStandardHelpOptions = true, version = "1.0-SNAPSHOT",
        description = "Calculate the number of days in-between two dates in the Common Era, following conventions of the Gregorian Calendar.")
public class Main implements Callable<Integer>{
    @Parameters(index = "0", description = "The first date, in the format YYYY-MM-DD.", converter = DateConverter.class)
    private Date date1;

    @Parameters(index = "1", description = "The second date, in the format YYYY-MM-DD.", converter = DateConverter.class)
    private Date date2;

    @Override
    public Integer call() throws Exception {
        final var dateString1 = "%04d-%02d-%02d".formatted(date1.year, date1.month, date1.day);
        final var dateString2 = "%04d-%02d-%02d".formatted(date2.year, date2.month, date2.day);

        System.out.println("Date (1) is: " + date1);
        System.out.println("Date (2) is: " + date2);

        final int diff = date2.daysBetween(date1);

        System.out.println("Days between is: " + diff);

        final var ld1 = LocalDate.parse(dateString1);
        final var ld2 = LocalDate.parse(dateString2);

        final var com = ld1.datesUntil(ld2).count();

        System.out.println("Java says: " + com);
        return 0;
    }

    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
