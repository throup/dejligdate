package eu.throup.dejligdate.exception;

public class InvalidYearException extends DateException {
    public InvalidYearException() {
        super();
    }

    public InvalidYearException(int year) {
        super("There is no year %d in the Gregorian calendar.".formatted(year));
    }
}
