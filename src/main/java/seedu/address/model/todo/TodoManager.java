package seedu.address.model.todo;

import seedu.address.model.item.ItemManager;

public class TodoManager extends ItemManager<Todo> {
    public TodoManager() {
        super(new UniqueTodoList());
    }
}
