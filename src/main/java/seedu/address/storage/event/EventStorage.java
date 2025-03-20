package seedu.address.storage.event;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.item.ItemManager;

/**
 * Represents a storage for {@link EventManager}.
 */
public interface EventStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getEventListFilePath();

    /**
     * Returns AddressBook data as a {@link ItemManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ItemManager<Event>> readEventList() throws DataLoadingException;

    /**
     * @see #getEventListFilePath()
     */
    Optional<ItemManager<Event>> readEventList(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ItemManager} to the storage.
     *
     * @param eventManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventList(ItemManager<Event> eventManager) throws IOException;

    /**
     * @see #saveEventList(ItemManager)
     */
    void saveEventList(ItemManager<Event> eventManager, Path filePath) throws IOException;

}
