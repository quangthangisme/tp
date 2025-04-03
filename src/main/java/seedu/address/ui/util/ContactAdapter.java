package seedu.address.ui.util;

import seedu.address.model.contact.Contact;
import seedu.address.ui.card.CardFactory;

/**
 * Adapter for Contact objects to implement DisplayableItem interface.
 */
public class ContactAdapter extends ItemAdapter<Contact> {
    /**
     * Constructs a ContactAdapter with the specified contact and card factory.
     *
     * @param contact The Contact object to adapt
     * @param cardFactory The factory used to create display cards for this contact
     */
    public ContactAdapter(Contact contact, CardFactory<Contact> cardFactory) {
        super(contact, cardFactory);
    }

    /**
     * Gets the contact being adapted.
     *
     * @return The contact
     */
    public Contact getContact() {
        return getEntity();
    }
}
