package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DatetimeUtil.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;

import seedu.address.commons.util.DatetimeUtil;

/**
 * Represents an Event's date and time.
 * Gurantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class EventDateTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Event start/end time should be in the format YY-MM-DD HH:MM, where HH is in 24-hour format.";
    public static final String MESSAGE_NEGATIVE_DURATION =
            "The given event start-time is not before the given end-time.";
    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code EventDateTime}
     *
     * @param dateTime A valid date and time in the format "YY-MM-DD HH:MM".
     */
    public EventDateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValid(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = DatetimeUtil.parse(dateTime);
    }

    /**
     * Returns true if a given string is a valid {@code LocalDateTime} format.
     */
    public static boolean isValid(String dateTime) {
        try {
            DatetimeUtil.parse(dateTime);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isBefore(EventDateTime otherEventDateTime) {
        return this.dateTime.isBefore(otherEventDateTime.dateTime);
    }

    @Override
    public String toString() {
        return this.dateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EventDateTime otherDateTime)) {
            return false;
        }
        return this.dateTime.equals(otherDateTime.dateTime);
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }
}
