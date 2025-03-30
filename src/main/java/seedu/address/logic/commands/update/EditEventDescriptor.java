package seedu.address.logic.commands.update;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventName;

/**
 * Stores the details to edit the event with. Each non-empty field value will replace the
 * corresponding field value of the event.
 */
public class EditEventDescriptor {

    private EventName name;
    private EventDateTime startTime;
    private EventDateTime endTime;
    private EventLocation location;
    private List<Contact> contacts;
    private List<Boolean> markedList;
    private Set<Tag> tags = new HashSet<>();

    public EditEventDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code contacts} and {@code tags} is used internally.
     */
    public EditEventDescriptor(EditEventDescriptor toCopy) {
        setName(toCopy.name);
        setStartTime(toCopy.startTime);
        setEndTime(toCopy.endTime);
        setLocation(toCopy.location);
        setContacts(toCopy.contacts);
        setMarkedList(toCopy.markedList);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, startTime, endTime, location, contacts, markedList, tags);
    }

    public Optional<EventName> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(EventName name) {
        this.name = name;
    }

    public Optional<EventDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    public void setStartTime(EventDateTime startTime) {
        this.startTime = startTime;
    }

    public Optional<EventDateTime> getEndTime() {
        return Optional.ofNullable(endTime);
    }

    public void setEndTime(EventDateTime endTime) {
        this.endTime = endTime;
    }

    public Optional<EventLocation> getLocation() {
        return Optional.ofNullable(location);
    }

    public void setLocation(EventLocation location) {
        this.location = location;
    }

    /**
     * Returns an unmodifiable contact list, which throws {@code UnsupportedOperationException} if
     * modification is attempted. Returns {@code Optional#empty()} if {@code contacts} is null.
     */
    public Optional<List<Contact>> getContacts() {
        return (contacts == null) ? Optional.empty() : Optional.of(List.copyOf(contacts));
    }

    /**
     * Sets {@code contacts} to this object's {@code contacts}. A defensive copy of {@code contacts}
     * is used internally.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = (contacts == null) ? null : List.copyOf(contacts);
    }

    /**
     * Returns an unmodifiable marked list, which throws {@code UnsupportedOperationException} if
     * modification is attempted. Returns {@code Optional#empty()} if {@code markedList} is null.
     */
    public Optional<List<Boolean>> getMarkedList() {
        return (markedList == null) ? Optional.empty() : Optional.of(List.copyOf(markedList));
    }

    /**
     * Sets {@code markedList} to this object's {@code markedList}. A defensive copy of {@code markedList}
     * is used internally.
     */
    public void setMarkedList(List<Boolean> markedList) {
        this.markedList = (markedList == null) ? null : List.copyOf(markedList);
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
                && Objects.equals(contacts, otherEditEventDescriptor.contacts)
                && Objects.equals(tags, otherEditEventDescriptor.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("location", location)
                .add("contacts", contacts)
                .add("tags", tags)
                .toString();
    }
}
