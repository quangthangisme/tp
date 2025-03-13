package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventName;

/**
 * Contains utilty methods used for parsing strings in the various classes parsing event commands.
 */
public class EventParseUtil {
    /**
     * Parses a {@code String name} into a {@code EventName}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static EventName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!EventName.isValid(trimmedName)) {
            throw new ParseException(EventName.MESSAGE_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses a {@code String dateTime} into a {@code EventDateTime}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code dateTime} is invalid.
     */
    public static EventDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        if (!EventDateTime.isValid(trimmedDateTime)) {
            throw new ParseException(EventDateTime.MESSAGE_CONSTRAINTS);
        }
        return new EventDateTime(trimmedDateTime);
    }

    /**
     * Parses a {@code String location} into a {@code EventLocation}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static EventLocation parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!EventLocation.isValid(trimmedLocation)) {
            throw new ParseException(EventLocation.MESSAGE_CONSTRAINTS);
        }
        return new EventLocation(trimmedLocation);
    }
}
