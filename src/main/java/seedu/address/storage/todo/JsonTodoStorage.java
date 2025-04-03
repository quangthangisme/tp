package seedu.address.storage.todo;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.todo.Todo;

/**
 * A class to access Todo data stored as a JSON file on the hard disk.
 */
public class JsonTodoStorage implements TodoStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTodoStorage.class);

    private final Path filePath;

    public JsonTodoStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTodoListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ItemInvolvingContactManager<Todo>> readTodoList(
            ItemNotInvolvingContactManager<Contact> contactManager
    ) throws DataLoadingException {
        return readTodoList(filePath, contactManager);
    }

    /**
     * Similar to {@link #readTodoList}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ItemInvolvingContactManager<Todo>> readTodoList(
            Path filePath, ItemNotInvolvingContactManager<Contact> contactManager
    ) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTodoManager> jsonTodoManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableTodoManager.class);
        return jsonTodoManager.map(jsonSerializableTodoManager ->
                jsonSerializableTodoManager.toModelType(contactManager));

    }

    @Override
    public void saveTodoList(ItemInvolvingContactManager<Todo> todoManager) throws IOException {
        saveTodoList(todoManager, filePath);
    }

    /**
     * Similar to {@link #saveTodoList(ItemInvolvingContactManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveTodoList(ItemInvolvingContactManager<Todo> todoManager, Path filePath) throws IOException {
        requireNonNull(todoManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTodoManager(todoManager), filePath);
    }
}
