package seedu.address.storage.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;
import seedu.address.model.todo.TodoStatus;
import seedu.address.storage.person.JsonAdaptedPerson;

/**
 * Jackson-friendly version of {@link Person}.
 */
public class JsonAdaptedTodo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String deadline;
    private final String location;
    private final String status;
    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTodo(
            @JsonProperty("name") String name,
            @JsonProperty("deadline") String deadline,
            @JsonProperty("location") String location,
            @JsonProperty("status") String status,
            @JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.name = name;
        this.deadline = deadline;
        this.location = location;
        this.status = status;
        if (persons != null) {
            this.persons.addAll(persons);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedTodo(Todo source) {
        name = source.getName().toString();
        deadline = source.getDeadline().toString();
        location = source.getLocation().toString();
        status = source.getStatus().toString();
        persons.addAll(source.getPersons().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Todo toModelType() throws IllegalValueException {
        final List<Person> todoPersons = new ArrayList<>();
        for (JsonAdaptedPerson person : persons) {
            todoPersons.add(person.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TodoName.class.getSimpleName()));
        }
        if (!TodoName.isValid(name)) {
            throw new IllegalValueException(TodoName.MESSAGE_CONSTRAINTS);
        }
        final TodoName todoName = new TodoName(name);

        if (deadline == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TodoDeadline.class.getSimpleName()));
        }
        if (!TodoDeadline.isValid(deadline)) {
            throw new IllegalValueException(TodoDeadline.MESSAGE_CONSTRAINTS);
        }
        final TodoDeadline todoDeadline = new TodoDeadline(deadline);

        if (location == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, TodoLocation.class.getSimpleName()));
        }
        if (!TodoLocation.isValid(location)) {
            throw new IllegalValueException(TodoLocation.MESSAGE_CONSTRAINTS);
        }
        final TodoLocation todoLocation = new TodoLocation(location);

        boolean isDone = status.equals("Done");
        final TodoStatus todoStatus = new TodoStatus(isDone);

        return new Todo(todoName, todoDeadline, todoLocation, todoStatus, todoPersons);
    }

}
