package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Todo's deadline.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class TodoDeadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be in the format YY-MM-DD HH:MM, where HH is in 24-hour format.";

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    public final LocalDateTime deadline;

    /**
     * Constructs a {@code TodoDeadline}.
     *
     * @param deadline A valid deadline in the format "YY-MM-DD HH:MM".
     */
    public TodoDeadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValid(deadline), MESSAGE_CONSTRAINTS);
        this.deadline = LocalDateTime.parse(deadline, DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValid(String test) {
        try {
            LocalDateTime.parse(test, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
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
