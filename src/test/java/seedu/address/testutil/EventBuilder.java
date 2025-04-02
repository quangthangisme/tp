package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Event stuff";
    public static final String DEFAULT_START = "25-12-31 23:59";
    public static final String DEFAULT_END = "26-12-31 23:59";
    public static final String DEFAULT_LOCATION = "EARTH";
    public static final Attendance DEFAULT_ATTENDANCE = new Attendance();
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private Name name;
    private Datetime start;
    private Datetime end;
    private Location location;
    private Attendance attendance;
    private Set<Tag> tags;


    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        start = new Datetime(DEFAULT_START);
        end = new Datetime(DEFAULT_END);
        location = new Location(DEFAULT_LOCATION);
        attendance = new Attendance(DEFAULT_ATTENDANCE);
        tags = Set.copyOf(DEFAULT_TAGS);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        start = eventToCopy.getStartTime();
        end = eventToCopy.getEndTime();
        location = eventToCopy.getLocation();
        attendance = new Attendance(eventToCopy.getAttendance());
        tags = Set.copyOf(eventToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the start {@code Datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withStart(String start) {
        this.start = new Datetime(start);
        return this;
    }

    /**
     * Sets the end {@code Datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withEnd(String end) {
        this.end = new Datetime(end);
        return this;
    }

    /**
     * Sets the {@code Attendance} of the {@code Event} that we are building.
     */
    public EventBuilder withAttendance(Attendance attendance) {
        this.attendance = new Attendance(attendance);
        return this;
    }

    /**
     * Sets the {@code Attendance} of the {@code Event} that we are building using a list of
     * contacts. The default attendance status is false.
     */
    public EventBuilder withAttendance(Contact... contacts) {
        this.attendance = new Attendance().add(Arrays.asList(contacts));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are
     * building.
     */
    public EventBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Event build() {
        return new Event(name, start, end, location, attendance, tags);
    }
}
