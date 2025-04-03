package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import seedu.address.model.contact.Contact;

/**
 * A manager that manages items that involves contacts and supports updating contact details.
 */
public abstract class ItemInvolvingContactManager<T extends ItemInvolvingContact<T>>
        extends ItemManager<T, UniqueItemInvolvingContactList<T>> {

    public ItemInvolvingContactManager(UniqueItemInvolvingContactList<T> items) {
        super(items);
    }

    /**
     * Find and replace all occurrences of a contact.
     */
    public void findAndReplace(Contact oldContact, Contact newContact) {
        requireNonNull(oldContact);
        items.findAndReplace(oldContact, newContact);
    }

    /**
     * Remove all occurrences of a contact.
     */
    public void findAndRemove(Contact oldContact) {
        requireNonNull(oldContact);
        items.findAndRemove(oldContact);
    }

    /**
     * Remove all occurrences of contacts.
     */
    public void removeAllContact() {
        items.removeAllContacts();
    }
}
