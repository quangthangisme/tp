package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.person.PersonStorage;
import seedu.address.storage.todo.TodoStorage;

/**
 * API of the Storage component
 */
public interface Storage extends PersonStorage, TodoStorage, EventStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Path getTodoListFilePath();

    @Override
    Path getEventListFilePath();

    @Override
    Optional<ItemManager<Person>> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ItemManager<Person> addressBook) throws IOException;

    @Override
    Optional<ItemManager<Todo>> readTodoList() throws DataLoadingException;

    @Override
    void saveTodoList(ItemManager<Todo> addressBook) throws IOException;

    @Override
    Optional<ItemManager<Event>> readEventList() throws DataLoadingException;

    @Override
    void saveEventList(ItemManager<Event> eventManager) throws IOException;
}
