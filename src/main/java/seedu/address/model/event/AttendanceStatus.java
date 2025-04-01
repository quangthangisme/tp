package seedu.address.model.event;

/**
 * Represents a contact attendance status in an event.
 * Guarantees: immutable
 */
public class AttendanceStatus {
    private static final String ATTENDED_LABEL = "X";
    private static final String NOT_ATTENDED_LABEL = " ";

    private final boolean hasAttended;

    /**
     * Constructs an {@code AttendanceStatus}.
     *
     * @param isDone A boolean representing the status.
     */
    public AttendanceStatus(boolean isDone) {
        this.hasAttended = isDone;
    }

    /**
     * Returns the boolean status.
     */
    public boolean hasAttended() {
        return hasAttended;
    }

    @Override
    public String toString() {
        return hasAttended ? ATTENDED_LABEL : NOT_ATTENDED_LABEL;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AttendanceStatus otherStatus)) {
            return false;
        }

        return hasAttended == otherStatus.hasAttended;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(hasAttended);
    }
}
