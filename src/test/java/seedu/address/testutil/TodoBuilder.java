package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Todo objects.
 */
public class TodoBuilder {

    public static final String DEFAULT_NAME = "Todo stuff";
    public static final String DEFAULT_DEADLINE = "25-12-31 23:59";
    public static final String DEFAULT_LOCATION = "EARTH";
    public static final boolean DEFAULT_STATUS = false;
    public static final List<Contact> DEFAULT_CONTACTS = List.of();
    public static final Set<Tag> DEFAULT_TAGS = new HashSet<>();

    private Name name;
    private Datetime deadline;
    private Location location;
    private TodoStatus status;
    private List<Contact> contacts;
    private Set<Tag> tags;


    /**
     * Creates a {@code TodoBuilder} with the default details.
     */
    public TodoBuilder() {
        name = new Name(DEFAULT_NAME);
        deadline = new Datetime(DEFAULT_DEADLINE);
        location = new Location(DEFAULT_LOCATION);
        status = new TodoStatus(DEFAULT_STATUS);
        contacts = List.copyOf(DEFAULT_CONTACTS);
        tags = Set.copyOf(DEFAULT_TAGS);
    }

    /**
     * Initializes the TodoBuilder with the data of {@code todoToCopy}.
     */
    public TodoBuilder(Todo todoToCopy) {
        name = todoToCopy.getName();
        deadline = todoToCopy.getDeadline();
        location = todoToCopy.getLocation();
        status = todoToCopy.getStatus();
        contacts = List.copyOf(todoToCopy.getContacts());
        tags = Set.copyOf(todoToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Todo} that we are building.
     */
    public TodoBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Todo} that we are building.
     */
    public TodoBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the {@code Datetime} of the {@code Todo} that we are building.
     */
    public TodoBuilder withDeadline(String deadline) {
        this.deadline = new Datetime(deadline);
        return this;
    }

    /**
     * Sets the {@code TodoStatus} of the {@code Todo} that we are building.
     */
    public TodoBuilder withStatus(boolean status) {
        this.status = new TodoStatus(status);
        return this;
    }

    /**
     * Sets the {@code List<Contact>} of the {@code Todo} that we are building.
     */
    public TodoBuilder withContacts(List<Contact> contacts) {
        this.contacts = List.copyOf(contacts);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Todo} that we are
     * building.
     */
    public TodoBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Todo build() {
        return new Todo(name, deadline, location, status, contacts, tags);
    }

}
