package seedu.address.ui.card;

import java.util.function.BiFunction;

/**
 * Generic implementation of CardFactory that uses a function to create cards.
 * This allows for a more flexible and reusable factory pattern implementation.
 *
 * @param <T> The type of entity for which cards are created
 */
public class GenericCardFactory<T> implements CardFactory<T> {
    private final BiFunction<T, Integer, Card<T>> cardCreator;

    /**
     * Creates a new GenericCardFactory with the specified card creator function.
     *
     * @param cardCreator A function that takes an entity and an index and returns a Card
     */
    public GenericCardFactory(BiFunction<T, Integer, Card<T>> cardCreator) {
        this.cardCreator = cardCreator;
    }

    @Override
    public Card<T> createCard(T item, int index) {
        return cardCreator.apply(item, index);
    }
}
