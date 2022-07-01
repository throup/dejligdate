package eu.throup.dejligdate.spike;

class InvalidMonthException extends DateException {
    public InvalidMonthException() {
        super();
    }

    public InvalidMonthException(int month) {
        super("There is no month %d in the Gregorian calender.".formatted(month));
    }
}
