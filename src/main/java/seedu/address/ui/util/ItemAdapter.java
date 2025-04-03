package seedu.address.ui.util;

import seedu.address.ui.UiPart;
import seedu.address.ui.card.CardFactory;

/**
 * Generic adapter that allows any type to implement DisplayableItem interface.
 *
 * @param <T> The type of entity being adapted
 */
public abstract class ItemAdapter<T> implements DisplayableItem {
    private final T entity;
    private final CardFactory<T> cardFactory;

    /**
     * Constructs a ItemAdapter with the specified entity and card factory.
     *
     * @param entity The entity object to adapt
     * @param cardFactory The factory used to create display cards for this entity
     */
    public ItemAdapter(T entity, CardFactory<T> cardFactory) {
        this.entity = entity;
        this.cardFactory = cardFactory;
    }

    /**
     * Gets the underlying entity.
     *
     * @return The entity
     */
    public T getEntity() {
        return entity;
    }

    @Override
    public UiPart<?> getDisplayCard(int index) {
        return cardFactory.createCard(entity, index).getUiPart();
    }
}
