package seedu.address.ui.card;

import seedu.address.model.todo.Todo;
import seedu.address.ui.UiPart;

public class TodoCardFactory implements CardFactory<Todo> {
    @Override
    public UiPart<?> createCard(Todo todo, int index) {
        return new TodoCard(todo, index);
    }
}
