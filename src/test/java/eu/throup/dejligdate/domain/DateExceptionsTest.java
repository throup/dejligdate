package eu.throup.dejligdate.domain;

import eu.throup.dejligdate.exception.InvalidDayException;
import eu.throup.dejligdate.exception.InvalidMonthException;
import eu.throup.dejligdate.exception.InvalidYearException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateExceptionsTest {
    @Test
    void throwsNoException_forValidInputs() {
        new Date(2020, 1, 2);
        new Date(2000, 2, 29); // leap year
    }

    @Test
    void throwsInvalidYearException_forYearZero() {
        assertThrows(InvalidYearException.class, () -> new Date(0, 1, 2));
    }

    @Test
    void throwsInvalidMonthException_forInvalidMonth() {
        assertThrows(InvalidMonthException.class, () -> new Date(2020, 0, 2));
        assertThrows(InvalidMonthException.class, () -> new Date(2020, 13, 2));
    }

    @Test
    void throwsInvalidDayException_forInvalidDay() {
        // No month accepts days <= 0
        assertThrows(InvalidDayException.class, () -> new Date(2020, 1, 0));

        // Each month has their own upper limits for a non-leap year
        assertThrows(InvalidDayException.class, () -> new Date(2018, 1, 32));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 2, 29));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 3, 32));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 4, 31));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 5, 32));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 6, 31));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 7, 32));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 8, 32));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 9, 31));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 10, 32));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 11, 31));
        assertThrows(InvalidDayException.class, () -> new Date(2018, 12, 32));

        // February gets a little special
        assertThrows(InvalidDayException.class, () -> new Date(2019, 2, 29));
        assertThrows(InvalidDayException.class, () -> new Date(2020, 2, 30));
        assertThrows(InvalidDayException.class, () -> new Date(2021, 2, 29));
        assertThrows(InvalidDayException.class, () -> new Date(1900, 2, 29));
        assertThrows(InvalidDayException.class, () -> new Date(2000, 2, 30));
        assertThrows(InvalidDayException.class, () -> new Date(2100, 2, 29));
    }
}
