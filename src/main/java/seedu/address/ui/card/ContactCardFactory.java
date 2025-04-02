package seedu.address.ui.card;

import seedu.address.model.contact.Contact;
import seedu.address.ui.UiPart;

/**
 * Factory for creating display cards for Contact objects.
 * Implements the CardFactory interface for Contact type.
 */
public class ContactCardFactory implements CardFactory<Contact> {
    @Override
    public UiPart<?> createCard(Contact contact, int index) {
        return new ContactCard(contact, index);
    }
}
