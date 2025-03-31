package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;

/**
 * Contains utility methods used for parsing strings in the various classes parsing todo commands.
 */
public class TodoParserUtil {
    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValid(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String deadline} into a {@code Datetime}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Datetime parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Datetime.isValid(trimmedDeadline)) {
            throw new ParseException(Datetime.MESSAGE_CONSTRAINTS);
        }
        return new Datetime(trimmedDeadline);
    }

    /**
     * Parses a {@code String location} into an {@code Location}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValid(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }
}
