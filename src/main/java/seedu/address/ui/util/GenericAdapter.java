package seedu.address.ui.util;

import seedu.address.ui.card.CardFactory;

/**
 * Generic adapter that can adapt any entity type to a DisplayableItem.
 * This eliminates the need for entity-specific adapter classes.
 *
 * @param <T> The type of entity being adapted
 */
public class GenericAdapter<T> extends ItemAdapter<T> {
    /**
     * Constructs a GenericAdapter with the specified entity and card factory.
     *
     * @param entity The entity object to adapt
     * @param cardFactory The factory used to create display cards for this entity
     */
    public GenericAdapter(T entity, CardFactory<T> cardFactory) {
        super(entity, cardFactory);
    }
}
