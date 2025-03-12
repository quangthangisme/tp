package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Manages a list of {@code Item} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 *
 * @param <T> the type of {@code Item} being managed, which must extend {@link Item}.
 */
public abstract class ItemManagerWithFilteredList<T extends Item> {
    private final Predicate<T> predicateShowAll = unused -> true;
    private final ItemManager<T> itemManager;
    private final FilteredList<T> filteredItems;

    /**
     * Constructs an {@code ItemManagerWithFilteredList} with the specified {@code ItemManager}.
     * Initializes the filtered item list to the list of items from the provided
     * {@code itemManager}.
     *
     * @throws NullPointerException if {@code itemManager} is {@code null}.
     */
    public ItemManagerWithFilteredList(ItemManager<T> itemManager) {
        requireAllNonNull(itemManager);
        this.itemManager = itemManager;
        filteredItems = new FilteredList<>(this.itemManager.getItemList());
    }

    //=========== ItemManager ======================================================================

    /**
     * Replaces the {@code ItemManager} data with the data in {@code itemManager}.
     */
    public void setItemManager(ItemManager<T> itemManager) {
        this.itemManager.resetData(itemManager);
    }

    /**
     * Returns the {@code ItemManager}
     */
    public ItemManager<T> getItemManager() {
        return itemManager;
    }

    /**
     * Returns true if an item with the same identity as {@code item} exists in the manager.
     */
    public boolean hasItem(T item) {
        requireNonNull(item);
        return itemManager.hasItem(item);
    }

    /**
     * Deletes the given item. The item must exist in the manager.
     */
    public void deleteItem(T target) {
        itemManager.removeItem(target);
    }

    /**
     * Adds the given item. {@code item} must not already exist in the manager.
     */
    public void addItem(T item) {
        itemManager.addItem(item);
        showAllItems();
    }

    /**
     * Replaces the given item {@code target} with {@code editedItem}. {@code target} must exist in
     * the manager. The identity of {@code editedItem} must not be the same as another existing item
     * in the manager.
     */
    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        itemManager.setItem(target, editedItem);
    }

    //=========== Filtered Item List Accessors =====================================================

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    public ObservableList<T> getFilteredItemsList() {
        return filteredItems;
    }

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredItemsList(Predicate<T> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    /**
     * Resets the filter of the filtered item list to show all items.
     */
    public void showAllItems() {
        filteredItems.setPredicate(predicateShowAll);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ItemManagerWithFilteredList<?> otherManagerAndList)) {
            return false;
        }

        return itemManager.equals(otherManagerAndList.itemManager)
                && filteredItems.equals(otherManagerAndList.filteredItems);
    }
}
