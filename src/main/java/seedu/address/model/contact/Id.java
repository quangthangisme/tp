package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id implements Comparable<Id> {

    public static final String MESSAGE_CONSTRAINTS =
            "IDs must consist of words not starting with a hyphen and separated by spaces, "
                    + "and it should not be blank";
    // Ensures string is not empty AND does not start with whitespace
    public static final String VALIDATION_REGEX = "^(?!\\s)(?!-)\\S+(?:\\s+(?!-)\\S+)*$";

    public final String fullId;

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        fullId = id;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = (Id) other;
        return fullId.equals(otherId.fullId);
    }

    @Override
    public int hashCode() {
        return fullId.hashCode();
    }

    @Override
    public int compareTo(Id other) {
        return this.fullId.compareTo(other.fullId);
    }
}
