package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import seedu.address.model.contact.Contact;

/**
 * An ItemManagerWithFilteredList that supports updating contact details.
 */
public abstract class ItemInvolvingContactManagerWithFilteredList<T extends ItemInvolvingContact<T>>
        extends ItemManagerWithFilteredList<T, ItemInvolvingContactManager<T>,
        UniqueItemInvolvingContactList<T>> {

    public ItemInvolvingContactManagerWithFilteredList(ItemInvolvingContactManager<T> itemManager) {
        super(itemManager);
    }

    /**
     * Find and replace all occurrences of a contact.
     */
    public void findAndReplace(Contact oldContact, Contact newContact) {
        requireNonNull(oldContact);
        itemManager.findAndReplace(oldContact, newContact);
    }

    /**
     * Remove all occurrences of a contact.
     */
    public void findAndRemove(Contact oldContact) {
        requireNonNull(oldContact);
        itemManager.findAndRemove(oldContact);
    }

    /**
     * Remove all occurrences of contacts.
     */
    public void removeAllContacts() {
        itemManager.removeAllContact();
    }
}
