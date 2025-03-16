package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.item.ItemManager;
import seedu.address.model.person.Person;

/**
 * API of the Storage component
 */
public interface Storage extends PersonStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ItemManager<Person>> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ItemManager<Person> addressBook) throws IOException;

}
