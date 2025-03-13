package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventDateTime {
    public static final String MESSAGE_CONSTRAINTS = 
            "Event start/end time should be in the format YY-MM-DD HH:MM, where HH is in 24-hour format.";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
    public final LocalDateTime dateTime;

    public EventDateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValid(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid {@code LocalDateTime} format.
     */
    public boolean isValid(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
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
