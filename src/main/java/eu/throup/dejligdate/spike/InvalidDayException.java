package eu.throup.dejligdate.spike;

class InvalidDayException extends DateException {
    public InvalidDayException() {
        super();
    }

    public InvalidDayException(int day) {
        super("There is no day %d for the given month in the Gregorian calender.".formatted(day));
    }
}
