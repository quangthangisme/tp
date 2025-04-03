package seedu.address.storage.event;

import static seedu.address.logic.EventMessages.MESSAGE_NEGATIVE_DURATION;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.AttendanceStatus;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.storage.contact.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing.";

    private final String name;
    private final String startTime;
    private final String endTime;
    private final String location;
    private final List<JsonAdaptedAttendancePair> attendance = new ArrayList<>();
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
            @JsonProperty("attendance") List<JsonAdaptedAttendancePair> attendance,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        if (attendance != null) {
            this.attendance.addAll(attendance);
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
        attendance.addAll(source.getAttendance().getList().stream()
                .map(JsonAdaptedAttendancePair::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               event.
     */
    public Event toModelType(ItemNotInvolvingContactManager<Contact> contactManager)
            throws IllegalValueException {
        final List<Pair<Contact, AttendanceStatus>> attendanceList = parseAttendance(contactManager);
        final Set<Tag> modelTags = parseTags();
        final Name eventName = parseName();
        final Datetime eventStartTime = parseStartTime();
        final Datetime eventEndTime = parseEndTime();
        validateEventDuration(eventStartTime, eventEndTime);
        final Location eventLocation = parseLocation();

        return new Event(eventName, eventStartTime, eventEndTime, eventLocation,
                new Attendance(attendanceList), modelTags);
    }

    private List<Pair<Contact, AttendanceStatus>> parseAttendance(
            ItemNotInvolvingContactManager<Contact> contactManager) throws IllegalValueException {
        final List<Pair<Contact, AttendanceStatus>> attendanceList = new ArrayList<>();
        final List<String> idList = new ArrayList<>();
        for (JsonAdaptedAttendancePair pair : attendance) {
            if (idList.contains(pair.id)) {
                throw new IllegalValueException("Duplicate linked contacts found.");
            }
            idList.add(pair.id);
            attendanceList.add(pair.toModelType(contactManager));
        }
        return attendanceList;
    }

    private Set<Tag> parseTags() throws IllegalValueException {
        final Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }
        return modelTags;
    }

    private Name parseName() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValid(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(name);
    }

    private Datetime parseStartTime() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Datetime.class.getSimpleName()));
        }
        if (!Datetime.isValid(startTime)) {
            throw new IllegalValueException(Datetime.MESSAGE_CONSTRAINTS);
        }
        return new Datetime(startTime);
    }

    private Datetime parseEndTime() throws IllegalValueException {
        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Datetime.class.getSimpleName()));
        }
        if (!Datetime.isValid(endTime)) {
            throw new IllegalValueException(Datetime.MESSAGE_CONSTRAINTS);
        }
        return new Datetime(endTime);
    }

    private void validateEventDuration(Datetime start, Datetime end) throws IllegalValueException {
        if (!start.isBefore(end)) {
            throw new IllegalValueException(MESSAGE_NEGATIVE_DURATION);
        }
    }

    private Location parseLocation() throws IllegalValueException {
        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValid(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(location);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("location", location)
                .add("attendance", attendance)
                .add("tag", tags).toString();
    }
}
