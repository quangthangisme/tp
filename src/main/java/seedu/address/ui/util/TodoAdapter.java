package seedu.address.ui.util;

import seedu.address.model.todo.Todo;
import seedu.address.ui.card.CardFactory;

/**
 * Adapter for Todo objects to implement DisplayableItem interface.
 */
public class TodoAdapter extends ItemAdapter<Todo> {
    /**
     * Constructs a TodoAdapter with the specified todo and card factory.
     *
     * @param todo The Todo object to adapt
     * @param cardFactory The factory used to create display cards for this todo
     */
    public TodoAdapter(Todo todo, CardFactory<Todo> cardFactory) {
        super(todo, cardFactory);
    }

    /**
     * Gets the todo being adapted.
     *
     * @return The todo
     */
    public Todo getTodo() {
        return getEntity();
    }
}
