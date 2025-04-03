package seedu.address.model.todo;

import java.util.Comparator;

import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemInvolvingContactManagerWithFilteredList;

/**
 * Manages a list of {@code Todo} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class TodoManagerWithFilteredList extends ItemInvolvingContactManagerWithFilteredList<Todo>
        implements TodoManagerAndList {
    public TodoManagerWithFilteredList(ItemInvolvingContactManager<Todo> todoManager) {
        super(todoManager);
    }

    public TodoManagerWithFilteredList() {
        super(new TodoManager());
    }

    @Override
    public Comparator<Todo> getDefaultComparator() {
        return Comparator.comparing(Todo::getStatus).thenComparing(Todo::getDeadline)
                .thenComparing(Todo::getName);
    }
}
