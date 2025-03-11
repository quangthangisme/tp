package seedu.address.model.todo;

import seedu.address.model.item.ItemManagerWithFilteredList;

public class TodoMangerWithFilteredList extends ItemManagerWithFilteredList<Todo> {
    public TodoMangerWithFilteredList() {
        super(new TodoManager());
    }
}
