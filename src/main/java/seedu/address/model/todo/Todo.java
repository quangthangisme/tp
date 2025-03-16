package seedu.address.model.todo;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.Item;
import seedu.address.model.person.Person;

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
    private final List<Person> persons;

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName name, TodoDeadline deadline, TodoLocation location, List<Person> persons) {
        requireAllNonNull(name, deadline, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.persons = persons.stream().toList();
    }

    /**
     * Every field must be present and not null.
     */
    public Todo(TodoName name, TodoDeadline deadline, TodoLocation location) {
        requireAllNonNull(name, deadline, location);
        this.name = name;
        this.deadline = deadline;
        this.location = location;
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

    public List<Person> getPersons() {
        return persons;
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
                && persons.equals(otherTodo.persons);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, deadline, location, persons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("deadline", deadline)
                .add("location", location)
                .add("persons", persons)
                .toString();
    }
}
