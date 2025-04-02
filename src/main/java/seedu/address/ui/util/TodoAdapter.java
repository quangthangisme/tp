package seedu.address.ui.util;

import seedu.address.model.todo.Todo;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.CardFactory;

public class TodoAdapter implements DisplayableItem {
    private final Todo todo;
    private final CardFactory<Todo> cardFactory;

    public TodoAdapter(Todo todo, CardFactory<Todo> cardFactory) {
        this.todo = todo;
        this.cardFactory = cardFactory;
    }

    public Todo getTodo() {
        return todo;
    }

    @Override
    public UiPart<?> getDisplayCard(int index) {
        return cardFactory.createCard(todo, index);
    }
}
