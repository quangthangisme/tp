package seedu.address.model.contact;

import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.item.ManagerAndList;

/**
 * The API of the manager and filtered list for contacts.
 */
public interface ContactManagerAndList extends ManagerAndList<Contact> {
    void setItemManager(ItemNotInvolvingContactManager<Contact> itemManager);

    ItemNotInvolvingContactManager<Contact> getItemManager();
}
