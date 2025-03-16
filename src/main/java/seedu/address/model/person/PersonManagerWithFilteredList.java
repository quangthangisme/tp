package seedu.address.model.person;

import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Manages a list of {@code Person} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class PersonManagerWithFilteredList extends ItemManagerWithFilteredList<Person> {
    public PersonManagerWithFilteredList(ItemManager<Person> personManager) {
        super(personManager);
    }

    public PersonManagerWithFilteredList() {
        super(new PersonManager());
    }
}
