package seedu.address.model.event;

import seedu.address.model.contact.Contact;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ManagerAndList;

/**
 * The API of the manager and filtered list for events.
 */
public interface EventManagerAndList extends ManagerAndList<Event> {
    void setItemManager(ItemInvolvingContactManager<Event> itemManager);

    ItemInvolvingContactManager<Event> getItemManager();

    void findAndReplace(Contact oldContact, Contact newContact);

    void findAndRemove(Contact oldContact);

    void removeAllContacts();
}
