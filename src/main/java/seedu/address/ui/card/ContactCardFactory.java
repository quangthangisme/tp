package seedu.address.ui.card;

import seedu.address.model.contact.Contact;
import seedu.address.ui.UiPart;

public class ContactCardFactory implements CardFactory<Contact> {
    @Override
    public UiPart<?> createCard(Contact contact, int index) {
        return new ContactCard(contact, index);
    }
}
