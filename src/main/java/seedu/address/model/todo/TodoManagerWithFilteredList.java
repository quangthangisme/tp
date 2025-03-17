package seedu.address.model.todo;

import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Manages a list of {@code Todo} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class TodoManagerWithFilteredList extends ItemManagerWithFilteredList<Todo> {
    public TodoManagerWithFilteredList(ItemManager<Todo> todoManager) {
        super(todoManager);
    }

    public TodoManagerWithFilteredList() {
        super(new TodoManager());
    }
}
