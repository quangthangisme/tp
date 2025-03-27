package seedu.address.model.todo.predicate;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.todo.Todo;

/**
 * Tests if a {@code Todo}'s status matches the specified boolean.
 */
public class TodoStatusPredicate implements Predicate<Todo> {
    private final boolean bool;

    /**
     * Constructs a {@code TodoStatusPredicate} with the given boolean.
     */
    public TodoStatusPredicate(boolean bool) {
        this.bool = bool;
    }

    @Override
    public boolean test(Todo todo) {
        return todo.getStatus().isDone() == bool;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoStatusPredicate otherPredicate)) {
            return false;
        }

        return bool == otherPredicate.bool;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("bool", bool).toString();
    }
}
