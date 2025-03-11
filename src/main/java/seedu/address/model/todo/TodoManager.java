package seedu.address.model.todo;

import seedu.address.model.item.ItemManager;

/**
 * Wraps all {@code Todo}-related data. Duplicates are not allowed.
 */
public class TodoManager extends ItemManager<Todo> {
    public TodoManager() {
        super(new UniqueTodoList());
    }
}
