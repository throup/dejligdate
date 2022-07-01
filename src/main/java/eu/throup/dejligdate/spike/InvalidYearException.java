package eu.throup.dejligdate.spike;

class InvalidYearException extends DateException {
    public InvalidYearException() {
        super();
    }

    public InvalidYearException(int year) {
        super("There is no year %d in the Gregorian calender.".formatted(year));
    }
}
