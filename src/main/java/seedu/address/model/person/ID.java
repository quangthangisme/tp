package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidID(String)}
 */
public class ID {

    public static final String MESSAGE_CONSTRAINTS =
            "IDs should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullID;

    /**
     * Constructs a {@code ID}.
     *
     * @param id A valid id.
     */
    public ID(String id) {
        requireNonNull(id);
        checkArgument(isValidID(id), MESSAGE_CONSTRAINTS);
        fullID = id;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidID(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullID;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ID)) {
            return false;
        }

        ID otherID = (ID) other;
        return fullID.equals(otherID.fullID);
    }

    @Override
    public int hashCode() {
        return fullID.hashCode();
    }

}
