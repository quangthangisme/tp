package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.Item;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Represents a Todo.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Todo implements Item {

    // Identity fields
    private final TodoName name;

    // Data fields
    private final TodoDeadline deadline;
    private final TodoLocation location;
    private final TodoStatus status;
    private final List<Person> persons;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName name, TodoDeadline deadline, TodoLocation location, TodoStatus status,
                List<Person> persons, Set<Tag> tags) {
        requireAllNonNull(name, deadline, status, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = status;
        this.persons = persons.stream().toList();
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName name, TodoDeadline deadline, TodoLocation location) {
        requireAllNonNull(name, deadline, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = new TodoStatus(false);
        this.persons = List.of();
    }

    public TodoName getName() {
        return name;
    }

    public TodoDeadline getDeadline() {
        return deadline;
    }

    public TodoLocation getLocation() {
        return location;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Set<Tag> getTags() {
        return this.tags;
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
                && persons.equals(otherTodo.persons)
                && tags.equals(otherTodo.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, location, status, persons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("deadline", deadline)
                .add("location", location)
                .add("status", status)
                .add("persons", persons)
                .add("tags", tags)
                .toString();
    }
}
