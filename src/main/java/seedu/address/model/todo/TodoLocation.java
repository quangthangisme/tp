package seedu.address.model.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Todo's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class TodoLocation {

    public static final String MESSAGE_CONSTRAINTS =
            "Locations can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code TodoLocation}.
     *
     * @param address A valid address.
     */
    public TodoLocation(String address) {
        requireNonNull(address);
        checkArgument(isValid(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoLocation otherLocation)) {
            return false;
        }

        return value.equals(otherLocation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
