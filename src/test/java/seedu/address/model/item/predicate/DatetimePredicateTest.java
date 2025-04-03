package seedu.address.model.item.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.DatetimeUtil;

class DatetimePredicateTest {

    @Test
    void constructor_validStartAndEndTime_success() {
        LocalDateTime start = DatetimeUtil.parse("24-07-25 10:00");
        LocalDateTime end = DatetimeUtil.parse("24-08-25 10:00");
        DatetimePredicate predicate = new DatetimePredicate(Optional.of(start), Optional.of(end));

        assertTrue(predicate.test(DatetimeUtil.parse("24-07-26 10:00")));
        assertFalse(predicate.test(DatetimeUtil.parse("24-08-26 10:00")));
    }

    @Test
    void constructor_emptyStartTime_success() {
        LocalDateTime end = DatetimeUtil.parse("24-07-25 10:00");
        DatetimePredicate predicate = new DatetimePredicate(Optional.empty(), Optional.of(end));

        assertTrue(predicate.test(DatetimeUtil.parse("24-07-24 10:00")));
        assertFalse(predicate.test(DatetimeUtil.parse("24-08-26 10:00")));
    }

    @Test
    void constructor_emptyEndTime_success() {
        LocalDateTime start = DatetimeUtil.parse("24-07-25 10:00");
        DatetimePredicate predicate = new DatetimePredicate(Optional.of(start), Optional.empty());

        assertTrue(predicate.test(DatetimeUtil.parse("24-07-26 10:00")));
        assertFalse(predicate.test(DatetimeUtil.parse("24-06-26 10:00")));
    }

    @Test
    void constructor_bothEmpty_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new DatetimePredicate(Optional.empty(), Optional.empty()));
    }

    @Test
    void constructor_validString_success() {
        DatetimePredicate predicate = new DatetimePredicate("[24-07-25 10:00/24-08-25 10:00]");
        assertTrue(predicate.test(DatetimeUtil.parse("24-07-25 10:00")));
        assertTrue(predicate.test(DatetimeUtil.parse("24-08-25 10:00")));
        assertFalse(predicate.test(DatetimeUtil.parse("24-06-26 10:00")));
    }

    @Test
    void constructor_invalidStringFormat_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new DatetimePredicate("invalid_format"));
    }

    @Test
    void constructor_invalidDate_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new DatetimePredicate("[24-07-25 10:00/invalid-date]"));
    }

    @Test
    void isValid_validCases_returnsTrue() {
        assertTrue(DatetimePredicate.isValid("[24-07-25 10:00/24-08-25 10:00]"));
        assertTrue(DatetimePredicate.isValid("[-/24-08-25 10:00]"));
        assertTrue(DatetimePredicate.isValid("[24-07-25 10:00/-]"));
    }

    @Test
    void isValid_invalidCases_returnsFalse() {
        assertFalse(DatetimePredicate.isValid("invalid_format"));
        assertFalse(DatetimePredicate.isValid("[24-07-25 10:00/invalid-date]"));
        assertFalse(DatetimePredicate.isValid("[-/-]"));
    }
}
