package eu.throup.dejligdate.exception;

public class InvalidMonthException extends DateException {
    public InvalidMonthException() {
        super();
    }

    public InvalidMonthException(int month) {
        super("There is no month %d in the Gregorian calendar.".formatted(month));
    }
}
