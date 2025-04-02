package seedu.address.ui.util;

import seedu.address.model.todo.Todo;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.CardFactory;

/**
 * Adapter for Todo objects to implement DisplayableItem interface.
 */
public class TodoAdapter implements DisplayableItem {
    private final Todo todo;
    private final CardFactory<Todo> cardFactory;

    /**
     * Constructs a TodoAdapter with the specified todo and card factory.
     *
     * @param todo The Todo object to adapt
     * @param cardFactory The factory used to create display cards for this todo
     */
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
