package seedu.address.model.todo;

import java.util.Comparator;

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

    @Override
    protected Comparator<Todo> getDefaultComparator() {
        return (todo1, todo2) -> {
            int statusComparison = todo1.getStatus().compareTo(todo2.getStatus());
            if (statusComparison != 0) {
                return statusComparison;
            }

            int deadlineComparison = todo1.getDeadline().compareTo(todo2.getDeadline());
            if (deadlineComparison != 0) {
                return deadlineComparison;
            }

            return todo1.getName().compareTo(todo2.getName());
        };
    }
}
