package seedu.address.storage.event;

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
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;

/**
 * A class to access Event data stored as a JSON file on the hard disk.
 */
public class JsonEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonEventStorage.class);

    private final Path filePath;

    public JsonEventStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getEventListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ItemManager<Event>> readEventList() throws DataLoadingException {
        return readEventList(filePath);
    }

    /**
     * Similar to {@link #readEventList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ItemManager<Event>> readEventList(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventManager> jsonEventManager = JsonUtil.readJsonFile(
            filePath, JsonSerializableEventManager.class);
        if (jsonEventManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveEventList(ItemManager<Event> eventManager) throws IOException {
        saveEventList(eventManager, filePath);
    }

    /**
     * Similar to {@link #saveEventList(ItemManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveEventList(ItemManager<Event> eventManager, Path filePath) throws IOException {
        requireNonNull(eventManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventManager(eventManager), filePath);
    }
}
