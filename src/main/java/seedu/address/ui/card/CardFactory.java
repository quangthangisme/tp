package seedu.address.ui.card;

/**
 * Factory for creating display cards for different entity types.
 */
public interface CardFactory<T> {
    /**
     * Creates a display card for the given item.
     *
     * @param item The item to display.
     * @param index The display index.
     * @return A Card that can render the item.
     */
    Card<T> createCard(T item, int index);
}
