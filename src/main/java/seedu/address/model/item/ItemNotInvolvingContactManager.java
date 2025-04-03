package seedu.address.model.item;

/**
 * An ItemManager that does not support updating contact details.
 */
public abstract class ItemNotInvolvingContactManager<T extends Item>
        extends ItemManager<T, UniqueItemList<T>> {

    /**
     * Creates a new ItemNotInvolvingContactManager.
     */
    public ItemNotInvolvingContactManager(UniqueItemList<T> items) {
        super(items);
    }
}
