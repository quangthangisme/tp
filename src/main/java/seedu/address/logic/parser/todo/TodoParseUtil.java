package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;

/**
 * Contains utility methods used for parsing strings in the various classes parsing todo commands.
 */
public class TodoParseUtil {
    /**
     * Parses a {@code String name} into a {@code TodoName}. Leading and trailing whitespaces will
     * be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static TodoName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!TodoName.isValid(trimmedName)) {
            throw new ParseException(TodoName.MESSAGE_CONSTRAINTS);
        }
        return new TodoName(trimmedName);
    }

    /**
     * Parses a {@code String deadline} into a {@code TodoDeadline}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static TodoDeadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!TodoDeadline.isValid(trimmedDeadline)) {
            throw new ParseException(TodoDeadline.MESSAGE_CONSTRAINTS);
        }
        return new TodoDeadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String location} into an {@code TodoLocation}. Leading and trailing
     * whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static TodoLocation parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!TodoLocation.isValid(trimmedLocation)) {
            throw new ParseException(TodoLocation.MESSAGE_CONSTRAINTS);
        }
        return new TodoLocation(trimmedLocation);
    }
}
