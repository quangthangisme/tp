package seedu.address.storage.todo;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonTodoStorage implements TodoStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTodoStorage.class);

    private Path filePath;

    public JsonTodoStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTodoListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ItemManager<Todo>> readTodoList() throws DataLoadingException {
        return readTodoList(filePath);
    }

    /**
     * Similar to {@link #readTodoList}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ItemManager<Todo>> readTodoList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTodoManager> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableTodoManager.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTodoList(ItemManager<Todo> todoManager) throws IOException {
        saveTodoList(todoManager, filePath);
    }

    /**
     * Similar to {@link #saveTodoList(ItemManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTodoList(ItemManager<Todo> todoManager, Path filePath) throws IOException {
        requireNonNull(todoManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTodoManager(todoManager), filePath);
    }

}

