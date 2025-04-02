package seedu.address.ui.util;

import seedu.address.model.contact.Contact;
import seedu.address.ui.UiPart;
import seedu.address.ui.card.CardFactory;

/**
 * Adapter for Contact objects to implement DisplayableItem interface.
 */
public class ContactAdapter implements DisplayableItem {
    private final Contact contact;
    private final CardFactory<Contact> cardFactory;

    public ContactAdapter(Contact contact, CardFactory<Contact> cardFactory) {
        this.contact = contact;
        this.cardFactory = cardFactory;
    }

    public Contact getContact() {
        return contact;
    }

    @Override
    public UiPart<?> getDisplayCard(int index) {
        return cardFactory.createCard(contact, index);
    }
}
