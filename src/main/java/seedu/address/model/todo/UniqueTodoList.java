package seedu.address.model.todo;

import seedu.address.model.item.UniqueItemList;
import seedu.address.model.todo.exceptions.DuplicateTodoException;
import seedu.address.model.todo.exceptions.TodoNotFoundException;

public class UniqueTodoList extends UniqueItemList<Todo> {

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
