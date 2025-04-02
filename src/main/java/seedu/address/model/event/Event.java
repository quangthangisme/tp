package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemWithLocation;
import seedu.address.model.item.NamedItem;
import seedu.address.model.item.TaggedItem;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.ui.util.DisplayableItem;
import seedu.address.ui.card.EventCard;
import seedu.address.ui.UiPart;

/**
 * Represents an Event.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements NamedItem, ItemWithLocation, TaggedItem, DisplayableItem {
    private final Name name;
    private final Datetime startTime;
    private final Datetime endTime;
    private final Location location;
    private final Attendance attendance;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null, field values are validated, immutable.
     */
    public Event(Name name, Datetime startTime, Datetime endTime,
                 Location location, Attendance attendance, Set<Tag> tags) {
        requireAllNonNull(name, startTime, endTime, location, attendance, tags);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendance = attendance;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null, field values are validated, immutable.
     */
    public Event(Name name, Datetime startTime,
            Datetime endTime, Location location) {
        requireAllNonNull(name, startTime, endTime, location);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendance = new Attendance();
    }

    /**
     * Every field must be present and not null.
     */
    public Event(Name name, Datetime startTime, Datetime endTime, Location location, Set<Tag> tagSet) {
        requireAllNonNull(name, startTime, endTime, location);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.attendance = new Attendance();
        this.tags.addAll(tagSet);
    }

    public Name getName() {
        return this.name;
    }

    public Datetime getStartTime() {
        return this.startTime;
    }

    public Datetime getEndTime() {
        return this.endTime;
    }

    public Location getLocation() {
        return this.location;
    }

    public Attendance getAttendance() {
        return this.attendance;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    @Override
    public UiPart<?> getDisplayCard(int index) {
        return new EventCard(this, index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("location", location)
                .add("attendance", attendance)
                .add("tags", tags)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Event otherEvent)) {
            return false;
        }
        return this.name.equals(otherEvent.name)
                && this.startTime.equals(otherEvent.startTime)
                && this.endTime.equals(otherEvent.endTime)
                && this.location.equals(otherEvent.location)
                && this.attendance.equals(otherEvent.attendance)
                && this.tags.equals(otherEvent.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startTime, endTime, location, attendance, tags);
    }
}
