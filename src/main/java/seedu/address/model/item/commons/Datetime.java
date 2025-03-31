package seedu.address.model.item.commons;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DatetimeUtil.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;

import seedu.address.commons.util.DatetimeUtil;

/**
 * Represents a datetime.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Datetime {

    public static final String MESSAGE_CONSTRAINTS =
            "Datetime should be in the format YY-MM-DD HH:MM, where HH is in 24-hour format.";

    public final LocalDateTime datetime;

    /**
     * Constructs a {@code Datetime}.
     *
     * @param deadline A valid deadline in the format "YY-MM-DD HH:MM".
     */
    public Datetime(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValid(deadline), MESSAGE_CONSTRAINTS);
        this.datetime = DatetimeUtil.parse(deadline);
    }


    /**
     * Check if a datetime is before another datetime.
     */
    public boolean isBefore(Datetime other) {
        return this.datetime.isBefore(other.datetime);
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValid(String test) {
        try {
            DatetimeUtil.parse(test);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return datetime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Datetime otherDeadline)) {
            return false;
        }

        return datetime.equals(otherDeadline.datetime);
    }

    @Override
    public int hashCode() {
        return datetime.hashCode();
    }
}
