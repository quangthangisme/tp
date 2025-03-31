package seedu.address.storage.todo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;
import seedu.address.storage.contact.JsonAdaptedContact;
import seedu.address.storage.contact.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Contact}.
 */
public class JsonAdaptedTodo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String name;
    private final String deadline;
    private final String location;
    private final boolean status;
    private final List<JsonAdaptedContact> contactList = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedTodo(
            @JsonProperty("name") String name,
            @JsonProperty("deadline") String deadline,
            @JsonProperty("location") String location,
            @JsonProperty("status") boolean status,
            @JsonProperty("contacts") List<JsonAdaptedContact> contactList,
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
        status = source.getStatus().isDone();
        contactList.addAll(source.getContacts().stream()
                .map(JsonAdaptedContact::new)
                .collect(Collectors.toList()));
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Todo toModelType() throws IllegalValueException {
        final List<Contact> todoContacts = new ArrayList<>();
        for (JsonAdaptedContact contact : contactList) {
            todoContacts.add(contact.toModelType());
        }

        final Set<Tag> modelTags = new HashSet<>();
        for (JsonAdaptedTag tag : tags) {
            modelTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValid(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name todoName = new Name(name);

        if (deadline == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Datetime.class.getSimpleName()));
        }
        if (!Datetime.isValid(deadline)) {
            throw new IllegalValueException(Datetime.MESSAGE_CONSTRAINTS);
        }
        final Datetime todoDeadline = new Datetime(deadline);

        if (location == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValid(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location todoLocation = new Location(location);

        final TodoStatus todoStatus = new TodoStatus(status);

        return new Todo(todoName, todoDeadline, todoLocation,
                todoStatus, todoContacts, modelTags);
    }

}
