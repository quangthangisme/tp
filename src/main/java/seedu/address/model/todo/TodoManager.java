package seedu.address.model.todo;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemInvolvingContactManager;

/**
 * Wraps all {@code Todo}-related data. Duplicates are not allowed.
 */
public class TodoManager extends ItemInvolvingContactManager<Todo> {
    public TodoManager() {
        super(new UniqueTodoList());
    }

    /**
     * Creates a TodoManager using the Todos in the {@code copy}
     */
    public TodoManager(ItemInvolvingContactManager<Todo> copy) {
        this();
        resetData(copy);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("todos", getItemList()).toString();
    }
}
