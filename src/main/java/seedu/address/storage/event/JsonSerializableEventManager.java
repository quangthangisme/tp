package seedu.address.storage.event;

import static seedu.address.storage.StorageMessage.MESSAGE_DUPLICATE_FOUND;
import static seedu.address.storage.StorageMessage.MESSAGE_INVALID_VALUE_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;

/**
 * An Immutable event list that is serializable to JSON format.
 */
@JsonRootName(value = "eventList")
class JsonSerializableEventManager {

    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";

    private static final Logger logger = LogsCenter.getLogger(JsonSerializableEventManager.class);

    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableEventManager} with the given events.
     */
    @JsonCreator
    public JsonSerializableEventManager(@JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.events.addAll(events);
    }

    /**
     * Converts a given {@code ItemManager<Event>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableEventManager(ItemInvolvingContactManager<Event> source) {
        events.addAll(source.getItemList().stream().map(JsonAdaptedEvent::new).toList());
    }

    /**
     * Converts this address book into the model's {@code EventManager} object.
     */
    public EventManager toModelType(ItemNotInvolvingContactManager<Contact> contactManager) {
        EventManager eventManager = new EventManager();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event;
            try {
                event = jsonAdaptedEvent.toModelType(contactManager);
            } catch (IllegalValueException e) {
                logger.info(String.format(MESSAGE_INVALID_VALUE_FOUND, jsonAdaptedEvent) + e.getMessage());
                continue;
            }

            if (eventManager.hasItem(event)) {
                logger.info(String.format(MESSAGE_DUPLICATE_FOUND, jsonAdaptedEvent));
                continue;
            }
            eventManager.addItem(event);
        }
        return eventManager;
    }

}
