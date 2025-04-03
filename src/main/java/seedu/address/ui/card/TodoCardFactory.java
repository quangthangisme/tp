package seedu.address.ui.card;

import seedu.address.model.todo.Todo;

/**
 * Factory for creating display cards for Todo objects.
 * Implements the CardFactory interface for Todo type.
 */
public class TodoCardFactory implements CardFactory<Todo> {
    @Override
    public Card<Todo> createCard(Todo todo, int index) {
        return new TodoCard(todo, index);
    }
}
