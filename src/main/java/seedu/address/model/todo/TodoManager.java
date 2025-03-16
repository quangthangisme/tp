package seedu.address.model.todo;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemManager;

/**
 * Wraps all {@code Todo}-related data. Duplicates are not allowed.
 */
public class TodoManager extends ItemManager<Todo> {
    public TodoManager() {
        super(new UniqueTodoList());
    }

    /**
     * Creates a TodoManager using the Todos in the {@code copy}
     */
    public TodoManager(ItemManager<Todo> copy) {
        this();
        resetData(copy);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("todos", getItemList()).toString();
    }
}
