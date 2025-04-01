package seedu.address.model.contact;

import java.util.Comparator;

import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Manages a list of {@code Contact} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class ContactManagerWithFilteredList extends ItemManagerWithFilteredList<Contact> {
    public ContactManagerWithFilteredList(ItemManager<Contact> contactManager) {
        super(contactManager);
    }

    public ContactManagerWithFilteredList() {
        super(new ContactManager());
    }

    @Override
    protected Comparator<Contact> getDefaultComparator() {
        return (contact1, contact2) -> {
            int nameComparison = contact1.getName().compareTo(contact2.getName());
            if (nameComparison != 0) {
                return nameComparison;
            }

            return contact1.getId().compareTo(contact2.getId());
        };
    }
}
