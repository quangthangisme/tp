package seedu.address.model.todo;

/**
 * Represents a Todo's status in the address book.
 * Guarantees: immutable
 */
public class TodoStatus {
    private final boolean isDone;

    /**
     * Constructs an {@code TodoStatus}.
     *
     * @param isDone A boolean representing the status.
     */
    public TodoStatus(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the boolean status of the todo.
     */
    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return isDone ? "Done" : "Not done";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TodoStatus otherStatus)) {
            return false;
        }

        return isDone == otherStatus.isDone;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isDone);
    }
}
