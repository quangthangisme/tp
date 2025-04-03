package seedu.address.model.item;

import seedu.address.model.contact.Contact;

/**
 * A list of items that involves contacts that support updating contact details.
 */
public abstract class UniqueItemInvolvingContactList<T extends ItemInvolvingContact<T>>
        extends UniqueItemList<T> {

    /**
     * Constructs a {@code UniqueItemList} with the given {@code duplicateChecker}.
     *
     * @param duplicateChecker the {@code DuplicateChecker} used to check for duplicates.
     */
    public UniqueItemInvolvingContactList(DuplicateChecker<T> duplicateChecker) {
        super(duplicateChecker);
    }

    /**
     * Find and replace all occurrences of a contact.
     */
    public void findAndReplace(Contact oldContact, Contact newContact) {
        for (T item: internalList) {
            if (item.involves(oldContact)) {
                setItem(item, item.setContact(oldContact, newContact));
            }
        }
    }

    /**
     * Remove all occurrences of a contact.
     */
    public void findAndRemove(Contact contact) {
        for (T item: internalList) {
            if (item.involves(contact)) {
                setItem(item, item.removeContact(contact));
            }
        }
    }

    /**
     * Remove all occurrences of contacts.
     */
    public void removeAllContacts() {
        for (T item: internalList) {
            setItem(item, item.removeAllContacts());
        }
    }
}
