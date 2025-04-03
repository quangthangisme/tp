package seedu.address.storage.event;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;

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
    Optional<ItemInvolvingContactManager<Event>> readEventList(
            ItemNotInvolvingContactManager<Contact> contactManager
    ) throws DataLoadingException;

    /**
     * @see #getEventListFilePath()
     */
    Optional<ItemInvolvingContactManager<Event>> readEventList(
            Path filePath, ItemNotInvolvingContactManager<Contact> contactManager
    ) throws DataLoadingException;

    /**
     * Saves the given {@link ItemInvolvingContactManager} to the storage.
     *
     * @param eventManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventList(ItemInvolvingContactManager<Event> eventManager) throws IOException;

    /**
     * @see #saveEventList(ItemInvolvingContactManager)
     */
    void saveEventList(ItemInvolvingContactManager<Event> eventManager, Path filePath) throws IOException;

}
