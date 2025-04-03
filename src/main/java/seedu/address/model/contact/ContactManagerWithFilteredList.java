package seedu.address.model.contact;

import java.util.Comparator;

import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManagerWithFilteredList;

/**
 * Manages a list of {@code Contact} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class ContactManagerWithFilteredList
        extends ItemNotInvolvingContactManagerWithFilteredList<Contact>
        implements ContactManagerAndList {
    public ContactManagerWithFilteredList(ItemNotInvolvingContactManager<Contact> contactManager) {
        super(contactManager);
    }

    public ContactManagerWithFilteredList() {
        super(new ContactManager());
    }

    @Override
    public Comparator<Contact> getDefaultComparator() {
        return Comparator.comparing(Contact::getName).thenComparing(Contact::getId);
    }
}
