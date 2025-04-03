package seedu.address.storage.event;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "eventlist")
class JsonSerializableEventManager {

    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";

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
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableEventManager(ItemInvolvingContactManager<Event> source) {
        events.addAll(source.getItemList().stream().map(JsonAdaptedEvent::new).toList());
    }

    /**
     * Converts this address book into the model's {@code EventManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public EventManager toModelType(ItemNotInvolvingContactManager<Contact> contactManager)
            throws IllegalValueException {
        EventManager eventManager = new EventManager();
        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType(contactManager);
            if (eventManager.hasItem(event)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EVENT);
            }
            eventManager.addItem(event);
        }
        return eventManager;
    }

}
