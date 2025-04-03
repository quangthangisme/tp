package seedu.address.model.todo;

import seedu.address.model.item.UniqueItemInvolvingContactList;
import seedu.address.model.todo.exceptions.DuplicateTodoException;
import seedu.address.model.todo.exceptions.TodoNotFoundException;

/**
 * A list of todos that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class UniqueTodoList extends UniqueItemInvolvingContactList<Todo> {

    public UniqueTodoList() {
        super(new TodoDuplicateChecker());
    }

    @Override
    public void throwDuplicateException() {
        throw new DuplicateTodoException();
    }

    @Override
    public void throwNotFoundException() {
        throw new TodoNotFoundException();
    }
}
