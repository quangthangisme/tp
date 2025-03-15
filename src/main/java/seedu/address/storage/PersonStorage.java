package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.item.ItemManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonManager;

/**
 * Represents a storage for {@link PersonManager}.
 */
public interface PersonManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ItemManager<Person>}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ItemManager<Person>> readAddressBook() throws DataLoadingException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ItemManager<Person>> readAddressBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ItemManager<Person>} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ItemManager<Person> addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ItemManager<Person>)
     */
    void saveAddressBook(ItemManager<Person> addressBook, Path filePath) throws IOException;

}
