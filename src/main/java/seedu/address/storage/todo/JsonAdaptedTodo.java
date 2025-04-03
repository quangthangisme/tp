package seedu.address.storage.todo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;
import seedu.address.storage.DummyContactBuilder;
import seedu.address.storage.contact.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Todo}.
 */
public class JsonAdaptedTodo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Todo's %s field is missing.";
    public static final String INVALID_ID_MESSAGE = "ID %s is invalid!";

    private final String name;
    private final String deadline;
    private final String location;
    private final String status;
    private final List<String> contactList = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedTodo(
            @JsonProperty("name") String name,
            @JsonProperty("deadline") String deadline,
            @JsonProperty("location") String location,
            @JsonProperty("status") String status,
            @JsonProperty("contacts") List<String> contactList,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = status;
        if (contactList != null) {
            this.contactList.addAll(contactList);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedTodo(Todo source) {
        name = source.getName().toString();
        deadline = source.getDeadline().toString();
        location = source.getLocation().toString();
        status = String.valueOf(source.getStatus().isDone());
        contactList.addAll(source.getContacts().stream()
                .map(contact -> contact.getId().toString())
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               contact.
     */
    public Todo toModelType(ItemNotInvolvingContactManager<Contact> contactManager)
            throws IllegalValueException {
        final List<Contact> todoContacts = parseContacts(contactManager);
        final Set<Tag> modelTags = parseTags();
        final Name todoName = parseName();
        final Datetime todoDeadline = parseDeadline();
        final Location todoLocation = parseLocation();
        final TodoStatus todoStatus = parseStatus();

        return new Todo(todoName, todoDeadline, todoLocation, todoStatus, todoContacts, modelTags);
    }

    private List<Contact> parseContacts(ItemNotInvolvingContactManager<Contact> contactManager)
            throws IllegalValueException {
        final List<Contact> todoContacts = new ArrayList<>();
        for (String id : contactList) {
            Id contactId = parseId(id);
            Contact contact = fetchContact(contactManager, contactId, id);
            if (todoContacts.contains(contact)) {
                throw new IllegalValueException("Duplicate linked contacts found.");
            }
            todoContacts.add(contact);
        }
        return todoContacts;
    }

    private Id parseId(String id) throws IllegalValueException {
        try {
            return new Id(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
    }

    private Contact fetchContact(ItemNotInvolvingContactManager<Contact> contactManager,
                                 Id contactId, String id) throws IllegalValueException {
        try {
            Contact dummyContact = DummyContactBuilder.build(contactId);
            return contactManager.getUpdateItem(dummyContact);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(String.format(INVALID_ID_MESSAGE, id));
        }
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

    private Datetime parseDeadline() throws IllegalValueException {
        if (deadline == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Datetime.class.getSimpleName()));
        }
        if (!Datetime.isValid(deadline)) {
            throw new IllegalValueException(Datetime.MESSAGE_CONSTRAINTS);
        }
        return new Datetime(deadline);
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

    private TodoStatus parseStatus() throws IllegalValueException {
        if (!"true".equals(status) && !"false".equals(status)) {
            throw new IllegalValueException("Status is not \"true\" or \"false\"!");
        }
        return new TodoStatus(Boolean.parseBoolean(status));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("location", location)
                .add("deadline", deadline)
                .add("status", status)
                .add("contacts", contactList)
                .add("tag", tags).toString();
    }
}
