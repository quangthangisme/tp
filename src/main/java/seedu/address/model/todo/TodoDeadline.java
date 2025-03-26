package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DatetimeUtil.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.util.DatetimeUtil;

/**
 * Represents a Todo's deadline.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class TodoDeadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be in the format YY-MM-DD HH:MM, where HH is in 24-hour format.";

    public final LocalDateTime deadline;

    /**
     * Constructs a {@code TodoDeadline}.
     *
     * @param deadline A valid deadline in the format "YY-MM-DD HH:MM".
     */
    public TodoDeadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValid(deadline), MESSAGE_CONSTRAINTS);
        this.deadline = DatetimeUtil.parse(deadline);
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
        return deadline.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoDeadline otherDeadline)) {
            return false;
        }

        return deadline.equals(otherDeadline.deadline);
    }

    @Override
    public int hashCode() {
        return deadline.hashCode();
    }
}
