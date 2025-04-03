package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all {@code T}-related data. Duplicates are not allowed.
 *
 * @param <T> the type of {@code Item} being managed, which extends {@link Item}.
 */
public abstract class ItemManager<T extends Item, U extends UniqueItemList<T>> {

    protected final U items;

    public ItemManager(U items) {
        this.items = items;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<T> items) {
        this.items.setItems(items);
    }

    /**
     * Resets the existing data of this {@code ItemManager} with {@code newData}.
     */
    public void resetData(ItemManager<T, U> newData) {
        requireNonNull(newData);

        setItems(newData.getItemList());
    }

    //// item-level operations

    /**
     * Returns true if an item with the same identity as {@code item} exists in the manager.
     */
    public boolean hasItem(T item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds an item to the manager.
     * The item must not already exist in the manager.
     */
    public void addItem(T p) {
        items.add(p);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the manager.
     * The identity of {@code editedItem} must not be the same as another existing item in the
     * manager.
     */
    public void setItem(T target, T editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code ItemManager}.
     * {@code key} must exist in the manager.
     */
    public void removeItem(T key) {
        items.remove(key);
    }

    /**
     * Returns an unmodifiable view of the item list.
     * This list will not contain any duplicate items.
     */
    public ObservableList<T> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    //// miscellaneous methods

    /**
     * Returns the duplicate checker instance used in the underlying list.
     */
    public DuplicateChecker<T> getDuplicateChecker() {
        return items.getDuplicateChecker();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemManager<?, ?> otherItemManager)) {
            return false;
        }

        return items.equals(otherItemManager.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
