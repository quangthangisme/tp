package seedu.address.ui;

import seedu.address.model.todo.Todo;

public class TodoCardFactory implements CardFactory<Todo> {
    @Override
    public UiPart<?> createCard(Todo todo, int index) {
        return new TodoCard(todo, index);
    }
}
