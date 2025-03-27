package seedu.address.storage.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
import seedu.address.storage.person.JsonAdaptedPerson;
import seedu.address.storage.person.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String startTime;
    private final String endTime;
    private final String location;
    private final List<JsonAdaptedPerson> personList = new ArrayList<>();
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
        @JsonProperty("persons") List<JsonAdaptedPerson> personList,
        @JsonProperty("markedList") List<Boolean> markedList,
        @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        if (personList != null) {
            this.personList.addAll(personList);
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
        personList.addAll(source.getPersons().stream()
            .map(JsonAdaptedPerson::new)
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
        final List<Person> eventPersonList = new ArrayList<>();
        for (JsonAdaptedPerson person : personList) {
            eventPersonList.add(person.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName()));
        }
        if (!EventName.isValid(name)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final EventName eventName = new EventName(name);

        if (startTime == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, EventDateTime.class.getSimpleName()));
        }
        if (!EventDateTime.isValid(startTime)) {
            throw new IllegalValueException(EventDateTime.MESSAGE_CONSTRAINTS);
        }
        final EventDateTime eventStartTime = new EventDateTime(startTime);

        if (endTime == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, EventDateTime.class.getSimpleName()));
        }
        if (!EventDateTime.isValid(endTime)) {
            throw new IllegalValueException(EventDateTime.MESSAGE_CONSTRAINTS);
        }
        final EventDateTime eventEndTime = new EventDateTime(endTime);

        if (location == null) {
            throw new IllegalValueException(String.format(
                MISSING_FIELD_MESSAGE_FORMAT, EventLocation.class.getSimpleName()));
        }
        if (!EventLocation.isValid(location)) {
            throw new IllegalValueException(EventLocation.MESSAGE_CONSTRAINTS);
        }
        final EventLocation eventLocation = new EventLocation(location);

        return new Event(eventName, eventStartTime, eventEndTime, eventLocation,
                eventPersonList, markedList, modelTags);
    }
}
