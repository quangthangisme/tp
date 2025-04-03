package seedu.address.model.todo;

import seedu.address.model.contact.Contact;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ManagerAndList;

/**
 * The API of the manager and filtered list for todos.
 */
public interface TodoManagerAndList extends ManagerAndList<Todo> {
    void setItemManager(ItemInvolvingContactManager<Todo> itemManager);

    ItemInvolvingContactManager<Todo> getItemManager();

    void findAndReplace(Contact oldContact, Contact newContact);

    void findAndRemove(Contact oldContact);

    void removeAllContacts();
}
