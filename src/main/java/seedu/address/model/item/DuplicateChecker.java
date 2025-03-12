package seedu.address.model.item;

/**
 * An interface for checking if two items are duplicates of each other.
 *
 * @param <T> the type of item to be checked for duplicates. This type must extend {@link Item}.
 */
public interface DuplicateChecker<T extends Item> {

    /**
     * Checks if two given items are considered duplicates of each other.
     *
     * @param first the first item to compare.
     * @param second the second item to compare.
     * @return {@code true} if the items are duplicates, {@code false} otherwise.
     */
    boolean check(T first, T second);
}
