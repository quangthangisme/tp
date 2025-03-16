package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemManager;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class PersonManager extends ItemManager<Person> {
    public PersonManager() {
        super(new UniquePersonList());
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public PersonManager(ItemManager<Person> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("persons", getItemList()).toString();
    }
}
