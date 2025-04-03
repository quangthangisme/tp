package seedu.address.storage.todo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;

/**
 * Represents a storage for {@link TodoManager}.
 */
public interface TodoStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTodoListFilePath();

    /**
     * Returns AddressBook data as a {@link ItemManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ItemInvolvingContactManager<Todo>> readTodoList() throws DataLoadingException;

    /**
     * @see #getTodoListFilePath()
     */
    Optional<ItemInvolvingContactManager<Todo>> readTodoList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ItemManager} to the storage.
     * @param todoManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTodoList(ItemInvolvingContactManager<Todo> todoManager) throws IOException;

    /**
     * @see #saveTodoList(ItemManager)
     */
    void saveTodoList(ItemInvolvingContactManager<Todo> todoManager, Path filePath) throws IOException;

}
