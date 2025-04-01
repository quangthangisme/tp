package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.item.commons.Name;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ContactParserUtil {

    /**
     * Parses a {@code String id} into an {@code Id}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code id} is invalid.
     */
    public static Id parseId(String id) throws ParseException {
        requireNonNull(id);
        String trimmedId = id.trim();
        if (!Id.isValidId(trimmedId)) {
            throw new ParseException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(trimmedId);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
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
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String course} into a {@code Course}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code course} is invalid.
     */
    public static Course parseCourse(String course) throws ParseException {
        requireNonNull(course);
        String trimmedCourse = course.trim();
        if (!Course.isValidCourse(trimmedCourse)) {
            throw new ParseException(Course.MESSAGE_CONSTRAINTS);
        }
        return new Course(trimmedCourse);
    }

    /**
     * Parses a {@code String group} into a {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws ParseException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Group.isValidGroup(trimmedGroup)) {
            throw new ParseException(Group.MESSAGE_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }
}
