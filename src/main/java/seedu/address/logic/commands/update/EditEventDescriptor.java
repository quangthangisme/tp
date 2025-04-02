package seedu.address.logic.commands.update;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Attendance;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * Stores the details to edit the event with. Each non-empty field value will replace the
 * corresponding field value of the event.
 */
public class EditEventDescriptor {

    private Name name;
    private Datetime startTime;
    private Datetime endTime;
    private Location location;
    private Attendance attendance;
    private Set<Tag> tags;

    public EditEventDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code attendance} and {@code tags} is used internally.
     */
    public EditEventDescriptor(EditEventDescriptor toCopy) {
        setName(toCopy.name);
        setStartTime(toCopy.startTime);
        setEndTime(toCopy.endTime);
        setLocation(toCopy.location);
        setAttendance(toCopy.attendance);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, startTime, endTime, location, attendance, tags);
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Datetime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    public void setStartTime(Datetime startTime) {
        this.startTime = startTime;
    }

    public Optional<Datetime> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    public void setEndTime(Datetime endTime) {
        this.endTime = endTime;
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns an unmodifiable attendance object.
     * Returns {@code Optional#empty()} if {@code attendance} is null.
     */
    public Optional<Attendance> getAttendance() {
        return (attendance == null) ? Optional.empty() : Optional.of(new Attendance(attendance));
    }

    /**
     * Sets {@code contacts} to this object's {@code contacts}. A defensive copy of {@code contacts}
     * is used internally.
     */
    public void setAttendance(Attendance attendance) {
        this.attendance = (attendance == null) ? null : new Attendance(attendance);
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted. Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used
     * internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventDescriptor otherEditEventDescriptor)) {
            return false;
        }

        return Objects.equals(name, otherEditEventDescriptor.name)
                && Objects.equals(startTime, otherEditEventDescriptor.startTime)
                && Objects.equals(endTime, otherEditEventDescriptor.endTime)
                && Objects.equals(location, otherEditEventDescriptor.location)
                && Objects.equals(attendance, otherEditEventDescriptor.attendance)
                && Objects.equals(tags, otherEditEventDescriptor.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("location", location)
                .add("contacts", attendance)
                .add("tags", tags)
                .toString();
    }
}
