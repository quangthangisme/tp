package seedu.address.storage.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.storage.contact.JsonAdaptedContact;
import seedu.address.storage.contact.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String startTime;
    private final String endTime;
    private final String location;
    private final List<JsonAdaptedContact> contactList = new ArrayList<>();
    private final List<Boolean> markedList = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(
        @JsonProperty("name") String name,
        @JsonProperty("startTime") String startTime,
        @JsonProperty("endTime") String endTime,
        @JsonProperty("location") String location,
        @JsonProperty("contacts") List<JsonAdaptedContact> contactList,
        @JsonProperty("markedList") List<Boolean> markedList,
        @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        if (contactList != null) {
            this.contactList.addAll(contactList);
        }
        if (markedList != null) {
            this.markedList.addAll(markedList);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        location = source.getLocation().toString();
        contactList.addAll(source.getContacts().stream()
            .map(JsonAdaptedContact::new)
            .collect(Collectors.toList()));
        markedList.addAll(source.getMarkedList());
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Contact> eventContactList = new ArrayList<>();
        for (JsonAdaptedContact contact : contactList) {
            eventContactList.add(contact.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValid(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name eventName = new Name(name);

        if (startTime == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Datetime.class.getSimpleName()));
        }
        if (!Datetime.isValid(startTime)) {
            throw new IllegalValueException(Datetime.MESSAGE_CONSTRAINTS);
        }
        final Datetime eventStartTime = new Datetime(startTime);

        if (endTime == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Datetime.class.getSimpleName()));
        }
        if (!Datetime.isValid(endTime)) {
            throw new IllegalValueException(Datetime.MESSAGE_CONSTRAINTS);
        }
        final Datetime eventEndTime = new Datetime(endTime);

        if (location == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValid(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location eventLocation = new Location(location);

        return new Event(eventName, eventStartTime, eventEndTime, eventLocation,
            eventContactList, markedList, modelTags);
    }
}
