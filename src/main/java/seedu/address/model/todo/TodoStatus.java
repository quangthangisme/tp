package seedu.address.model.todo;

/**
 * Represents a Todo's status in the address book.
 * Guarantees: immutable
 */
public class TodoStatus implements Comparable<TodoStatus> {
    private static final String DONE_LABEL = "Done";
    private static final String NOT_DONE_LABEL = "Not done";

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
    public int compareTo(TodoStatus other) {
        // Not done (false) comes before done (true)
        return Boolean.compare(this.isDone, other.isDone);
    }

    @Override
    public String toString() {
        return isDone ? DONE_LABEL : NOT_DONE_LABEL;
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
