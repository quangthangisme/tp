package seedu.address.model.item;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

/**
 * The API of the manager and filtered list for an item.
 */
public interface ManagerAndList<T extends Item> {

    Comparator<T> getDefaultComparator();

    boolean hasItem(T item);

    void deleteItem(T target);

    void addItem(T item);

    void setItem(T target, T editedItem);

    DuplicateChecker<T> getDuplicateChecker();

    ObservableList<T> getFilteredItemsList();

    void updateFilteredItemsList(Predicate<? super T> predicate);

    void showAllItems();
}
