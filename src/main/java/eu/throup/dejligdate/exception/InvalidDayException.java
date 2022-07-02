package eu.throup.dejligdate.exception;

public class InvalidDayException extends DateException {
    public InvalidDayException() {
        super();
    }

    public InvalidDayException(int day) {
        super("There is no day %d for the given month in the Gregorian calendar.".formatted(day));
    }
}
