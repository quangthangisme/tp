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

    /**
     * Constructs a ContactAdapter with the specified contact and card factory.
     *
     * @param contact The Contact object to adapt
     * @param cardFactory The factory used to create display cards for this contact
     */
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
