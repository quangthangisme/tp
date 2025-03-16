package seedu.address.model.person;

import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

public class PersonManagerWithFilteredList extends ItemManagerWithFilteredList<Person> {
    public PersonManagerWithFilteredList(ItemManager<Person> personManager) {
        super(personManager);
    }

    public PersonManagerWithFilteredList() {
        super(new PersonManager());
    }
}
