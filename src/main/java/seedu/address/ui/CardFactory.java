package seedu.address.ui;

/**
 * Factory for creating display cards for different entity types.
 */
public interface CardFactory<T> {
    /**
     * Creates a display card for the given item.
     *
     * @param item The item to display.
     * @param index The display index.
     * @return A UiPart that can render the item.
     */
    UiPart<?> createCard(T item, int index);
}
