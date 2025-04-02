package seedu.address.ui;

import seedu.address.model.contact.Contact;

public class ContactCardFactory implements CardFactory<Contact> {
    @Override
    public UiPart<?> createCard(Contact contact, int index) {
        return new ContactCard(contact, index);
    }
}
