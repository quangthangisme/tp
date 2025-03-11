package seedu.address.model.todo;

import seedu.address.model.item.DuplicateChecker;

public class TodoDuplicateChecker implements DuplicateChecker<Todo> {
    @Override
    public boolean check(Todo first, Todo second) {
        return first == second ||  (first != null && first.getName().equals(second.getName()));
    }
}
