package eu.throup.dejligdate.cli;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import scala.concurrent.impl.FutureConvertersImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainExamplesTest {
    static private PrintStream systemOut = null;
    static private PrintStream systemErr = null;

    @BeforeAll
    static void preserveSystemOut() {
        systemOut = System.out;
        systemErr = System.err;
    }

    @AfterAll
    static void restoreSystemOut() {
        System.setOut(systemOut);
        System.setErr(systemErr);
    }

    private ByteArrayOutputStream capturedOut = null;
    private ByteArrayOutputStream capturedErr = null;

    @BeforeEach
    void captureOutput() {
        capturedOut = new ByteArrayOutputStream();
        capturedErr = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
        System.setErr(new PrintStream(capturedErr));
    }

    @Test
    void givenNoInputs_displays_helpMessage() {
        runApp();
        final var output = capturedErr.toString();

        final var expectedOutput = """
                Missing required parameters: '<date1>', '<date2>'
                Usage: dejligdate [-hv] <date1> <date2>
                Calculate the number of days in-between two dates in the Common Era, following
                conventions of the Gregorian Calendar.
                      <date1>     The first date, in the format YYYY-MM-DD.
                      <date2>     The second date, in the format YYYY-MM-DD.
                  -h, --help      display this help message
                  -v, --verbose   Include information about the calculation, not just the
                                    result.
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenNoInputs_exitsWith_USAGE() {
        final var exitCode = runApp();

        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void givenHelpFlag_displays_helpMessage() {
        runApp("--help");
        final var output = capturedOut.toString();

        final var expectedOutput = """
                Usage: dejligdate [-hv] <date1> <date2>
                Calculate the number of days in-between two dates in the Common Era, following
                conventions of the Gregorian Calendar.
                      <date1>     The first date, in the format YYYY-MM-DD.
                      <date2>     The second date, in the format YYYY-MM-DD.
                  -h, --help      display this help message
                  -v, --verbose   Include information about the calculation, not just the
                                    result.
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenHelpFlag_exitsWith_OK() {
        final var exitCode = runApp("-h");

        assertEquals(CommandLine.ExitCode.OK, exitCode);
    }

    @Test
    void givenValidDates_displays_differenceInDays() {
        runApp("1963-11-23", "2005-03-26");
        final var output = capturedOut.toString();

        final var expectedOutput = """
                15099
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenValidDates_exitsWith_OK() {
        final var exitCode = runApp("1963-11-23", "2005-03-26");

        assertEquals(CommandLine.ExitCode.OK, exitCode);
    }

    @Test
    void givenValidDates_withverboseFlag_displays_descriptionOfDifference() {
        runApp("-v", "1963-11-23", "2005-03-26");
        final var output = capturedOut.toString();

        final var expectedOutput = """
                First date   : 1963-11-23
                Second date  : 2005-03-26
                Days between : 15099
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenValidDates_withverboseFlag_exitsWith_OK() {
        final var exitCode = runApp("-v", "1963-11-23", "2005-03-26");

        assertEquals(CommandLine.ExitCode.OK, exitCode);
    }

    @Test
    void givenInvalidFirstDate_displays_errorMessage() {
        runApp("1963-21-23", "2005-03-26");
        final var output = capturedErr.toString();

        final var expectedOutput = """
                Invalid value for positional parameter at index 0 (<date1>): cannot convert '1963-21-23' to Date (eu.throup.dejligdate.exception.InvalidMonthException: There is no month 21 in the Gregorian calendar.)
                Usage: dejligdate [-hv] <date1> <date2>
                Calculate the number of days in-between two dates in the Common Era, following
                conventions of the Gregorian Calendar.
                      <date1>     The first date, in the format YYYY-MM-DD.
                      <date2>     The second date, in the format YYYY-MM-DD.
                  -h, --help      display this help message
                  -v, --verbose   Include information about the calculation, not just the
                                    result.
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenInvalidFirstDate_exitsWith_USAGE() {
        final var exitCode = runApp("1963-21-23", "2005-03-26");

        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void givenInvalidSecondDate_displays_errorMessage() {
        runApp("1963-11-23", "2005-03-36");
        final var output = capturedErr.toString();

        final var expectedOutput = """
                Invalid value for positional parameter at index 1 (<date2>): cannot convert '2005-03-36' to Date (eu.throup.dejligdate.exception.InvalidDayException: There is no day 36 for the given month in the Gregorian calendar.)
                Usage: dejligdate [-hv] <date1> <date2>
                Calculate the number of days in-between two dates in the Common Era, following
                conventions of the Gregorian Calendar.
                      <date1>     The first date, in the format YYYY-MM-DD.
                      <date2>     The second date, in the format YYYY-MM-DD.
                  -h, --help      display this help message
                  -v, --verbose   Include information about the calculation, not just the
                                    result.
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenInvalidSecondDate_exitsWith_USAGE() {
        final var exitCode = runApp("1963-11-23", "2005-03-36");

        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void givenNonsenseInputs_displays_errorMessage() {
        runApp("Nonsense", "Inputs");
        final var output = capturedErr.toString();

        final var expectedOutput = """
                Invalid value for positional parameter at index 0 (<date1>): cannot convert 'Nonsense' to Date (eu.throup.dejligdate.exception.DateException: Unable to parse date: Nonsense.
                Valid dates have the format YYYY-MM-DD.)
                Usage: dejligdate [-hv] <date1> <date2>
                Calculate the number of days in-between two dates in the Common Era, following
                conventions of the Gregorian Calendar.
                      <date1>     The first date, in the format YYYY-MM-DD.
                      <date2>     The second date, in the format YYYY-MM-DD.
                  -h, --help      display this help message
                  -v, --verbose   Include information about the calculation, not just the
                                    result.
                """;

        assertEquals(expectedOutput, output);
    }

    @Test
    void givenNonsenseInputs_exitsWith_USAGE() {
        final var exitCode = runApp("Nonsense", "Inputs");

        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    private int runApp(String... args) {
        final var exitCode = Main.runToExitCode(args);
        System.out.flush();
        System.err.flush();
        return exitCode;
    }
}
