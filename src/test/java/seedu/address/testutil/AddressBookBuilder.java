package seedu.address.testutil;

import seedu.address.model.person.PersonManager;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private PersonManager personManager;

    public AddressBookBuilder() {
        personManager = new PersonManager();
    }

    public AddressBookBuilder(PersonManager personManager) {
        this.personManager = personManager;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        personManager.addItem(person);
        return this;
    }

    public PersonManager build() {
        return personManager;
    }
}
