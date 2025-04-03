package seedu.address.ui.card;

import seedu.address.model.contact.Contact;

/**
 * Factory for creating display cards for Contact objects.
 * Implements the CardFactory interface for Contact type.
 */
public class ContactCardFactory implements CardFactory<Contact> {
    @Override
    public Card<Contact> createCard(Contact contact, int index) {
        return new ContactCard(contact, index);
    }
}
