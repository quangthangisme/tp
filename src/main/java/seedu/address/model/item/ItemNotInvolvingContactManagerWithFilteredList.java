package seedu.address.model.item;

/**
 * An ItemManagerWithFilteredList that does not support updating contact details.
 */
public abstract class ItemNotInvolvingContactManagerWithFilteredList<T extends Item>
        extends ItemManagerWithFilteredList<T, ItemNotInvolvingContactManager<T>, UniqueItemList<T>> {

    /**
     * Creates a new ItemNotInvolvingContactManagerWithFilteredList.
     */
    public ItemNotInvolvingContactManagerWithFilteredList(ItemNotInvolvingContactManager<T> itemManager) {
        super(itemManager);
    }
}
