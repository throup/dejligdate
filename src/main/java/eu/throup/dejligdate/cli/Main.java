package eu.throup.dejligdate.cli;

import eu.throup.dejligdate.domain.Date;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "dejligdate", mixinStandardHelpOptions = true, version = "1.0-SNAPSHOT",
        description = "Calculate the number of days in-between two dates in the Common Era, following conventions of the Gregorian Calendar.")
public class Main implements Callable<Integer>{
    @CommandLine.Parameters(index = "0", description = "The first date, in the format YYYY-MM-DD.", converter = DateConverter.class)
    private Date date1;

    @CommandLine.Parameters(index = "1", description = "The second date, in the format YYYY-MM-DD.", converter = DateConverter.class)
    private Date date2;

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Include information about the calculation, not just the result.")
    private boolean verbose;

    @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true, description = "display this help message")
    private boolean helpRequested = false;

    @Override
    public Integer call() throws Exception {
        final int diff = date2.daysBetween(date1);

        if (verbose) {
            System.out.println("First date   : " + date1.formatted());
            System.out.println("Second date  : " + date2.formatted());
            System.out.println("Days between : " + diff);
        } else {
            System.out.println(diff);
        }

        return 0;
    }

    public static void main(String... args) {
        int exitCode = runToExitCode(args);
        System.exit(exitCode);
    }

    static int runToExitCode(String... args) {
        return new CommandLine(new Main()).execute(args);
    }
}
