package seedu.address.model.item;

import seedu.address.model.contact.Contact;

/**
 * An Item that involves contacts (via link/unlink).
 */
public interface ItemInvolvingContact<T extends Item> extends Item {
    boolean involves(Contact contact);

    T setContact(Contact oldContact, Contact newContact);

    T removeContact(Contact contact);

    T removeAllContacts();
}
