package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's name. Guarantees: immutable; is valid as declared in
 * {@link #isValid(String)}
 */
public class EventName {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String name;

    /**
     * Constructs a {@code EventName}.
     *
     * @param name A valid name.
     */
    public EventName(String name) {
        requireNonNull(name);
        checkArgument(isValid(name), MESSAGE_CONSTRAINTS);
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventName otherName)) {
            return false;
        }

        return name.equals(otherName.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
