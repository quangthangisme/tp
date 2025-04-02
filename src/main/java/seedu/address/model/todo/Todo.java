package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.ItemWithLocation;
import seedu.address.model.item.NamedItem;
import seedu.address.model.item.TaggedItem;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.ui.DisplayableItem;
import seedu.address.ui.TodoCard;
import seedu.address.ui.UiPart;

/**
 * Represents a Todo.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Todo implements NamedItem, ItemWithLocation, TaggedItem, DisplayableItem {

    // Identity fields
    private final Name name;

    // Data fields
    private final Datetime deadline;
    private final Location location;
    private final TodoStatus status;
    private final List<Contact> contacts;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Todo(Name name, Datetime deadline, Location location, TodoStatus status,
                List<Contact> contacts, Set<Tag> tags) {
        requireAllNonNull(name, deadline, status, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = status;
        this.contacts = contacts.stream().toList();
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Todo(Name name, Datetime deadline, Location location) {
        requireAllNonNull(name, deadline, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = new TodoStatus(false);
        this.contacts = List.of();
    }

    /**
     * Every field must be present and not null.
     */
    public Todo(Name name, Datetime deadline, Location location, Set<Tag> tagSet) {
        requireAllNonNull(name, deadline, location, tagSet);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = new TodoStatus(false);
        this.contacts = List.of();
        this.tags.addAll(tagSet);
    }

    public Name getName() {
        return name;
    }

    public Datetime getDeadline() {
        return deadline;
    }

    public Location getLocation() {
        return location;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    @Override
    public UiPart<?> getDisplayCard(int index) {
        return new TodoCard(this, index);
    }

    /**
     * Returns true if both todos have the same identity and data fields.
     * This defines a stronger notion of equality between two todos.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Todo otherTodo)) {
            return false;
        }

        return name.equals(otherTodo.name)
                && deadline.equals(otherTodo.deadline)
                && location.equals(otherTodo.location)
                && status.equals(otherTodo.status)
                && contacts.equals(otherTodo.contacts)
                && tags.equals(otherTodo.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, location, status, contacts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("deadline", deadline)
                .add("location", location)
                .add("status", status)
                .add("contacts", contacts)
                .add("tags", tags)
                .toString();
    }
}
