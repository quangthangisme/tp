package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.todo.Todo;
import seedu.address.storage.contact.ContactStorage;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.todo.TodoStorage;

/**
 * API of the Storage component
 */
public interface Storage extends ContactStorage, TodoStorage, EventStorage, UserPrefsStorage {

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
    Optional<ItemNotInvolvingContactManager<Contact>> readAddressBook() throws DataLoadingException;

    @Override
    void saveAddressBook(ItemNotInvolvingContactManager<Contact> addressBook) throws IOException;

    @Override
    Optional<ItemInvolvingContactManager<Todo>> readTodoList() throws DataLoadingException;

    @Override
    void saveTodoList(ItemInvolvingContactManager<Todo> addressBook) throws IOException;

    @Override
    Optional<ItemInvolvingContactManager<Event>> readEventList() throws DataLoadingException;

    @Override
    void saveEventList(ItemInvolvingContactManager<Event> eventManager) throws IOException;
}
