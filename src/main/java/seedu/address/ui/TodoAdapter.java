package seedu.address.ui;

import seedu.address.model.todo.Todo;

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
