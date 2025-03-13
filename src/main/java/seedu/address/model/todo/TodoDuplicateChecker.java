package seedu.address.model.todo;

import seedu.address.model.item.DuplicateChecker;

/**
 * Checks if two given todos are considered duplicates of each other.
 */
public class TodoDuplicateChecker implements DuplicateChecker<Todo> {
    @Override
    public boolean check(Todo first, Todo second) {
        return first == second || (first != null && first.getName().equals(second.getName()));
    }
}
