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
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.TodoStatus;

/**
 * Stores the details to edit the todo with. Each non-empty field value will replace the
 * corresponding field value of the todo.
 */
public class EditTodoDescriptor {

    // Identity fields
    private Name name;

    // Data fields
    private Datetime deadline;
    private Location location;
    private TodoStatus status;
    private List<Contact> contacts;
    private Set<Tag> tags;

    public EditTodoDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code contacts} and {@code tags} is used internally.
     */
    public EditTodoDescriptor(EditTodoDescriptor toCopy) {
        setName(toCopy.name);
        setDeadline(toCopy.deadline);
        setLocation(toCopy.location);
        setStatus(toCopy.status);
        setContacts(toCopy.contacts);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, deadline, location, status, contacts, tags);
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Datetime> getDeadline() {
        return Optional.ofNullable(deadline);
    }

    public void setDeadline(Datetime deadline) {
        this.deadline = deadline;
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Optional<TodoStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
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
        if (!(other instanceof EditTodoDescriptor otherEditTodoDescriptor)) {
            return false;
        }

        return Objects.equals(name, otherEditTodoDescriptor.name) && Objects.equals(deadline,
                otherEditTodoDescriptor.deadline) && Objects.equals(location, otherEditTodoDescriptor.location)
                && Objects.equals(status, otherEditTodoDescriptor.status) && Objects.equals(contacts,
                otherEditTodoDescriptor.contacts) && Objects.equals(tags, otherEditTodoDescriptor.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).add("deadline", deadline).add("location", location)
                .add("status", status).add("contacts", contacts).add("tags", tags).toString();
    }
}
