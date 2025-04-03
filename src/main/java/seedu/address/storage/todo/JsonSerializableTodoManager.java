package seedu.address.storage.todo;

import static seedu.address.storage.StorageMessage.MESSAGE_DUPLICATE_FOUND;
import static seedu.address.storage.StorageMessage.MESSAGE_INVALID_VALUE_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;

/**
 * An Immutable todo list that is serializable to JSON format.
 */
@JsonRootName(value = "todoList")
class JsonSerializableTodoManager {

    public static final String MESSAGE_DUPLICATE_TODO = "Todo list contains duplicate todo(s).";

    private static final Logger logger = LogsCenter.getLogger(JsonSerializableTodoManager.class);

    private final List<JsonAdaptedTodo> todos = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTodoManager} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableTodoManager(@JsonProperty("todos") List<JsonAdaptedTodo> todos) {
        this.todos.addAll(todos);
    }

    /**
     * Converts a given {@code ItemManager<Todo>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableTodoManager(ItemInvolvingContactManager<Todo> source) {
        todos.addAll(source.getItemList().stream().map(JsonAdaptedTodo::new).toList());
    }

    /**
     * Converts this address book into the model's {@code TodoManager} object.
     */
    public TodoManager toModelType(ItemNotInvolvingContactManager<Contact> contactManager) {
        TodoManager todoManager = new TodoManager();
        for (JsonAdaptedTodo jsonAdaptedTodo : todos) {
            Todo todo;
            try {
                todo = jsonAdaptedTodo.toModelType(contactManager);
            } catch (IllegalValueException e) {
                logger.info(String.format(MESSAGE_INVALID_VALUE_FOUND, jsonAdaptedTodo)
                        + e.getMessage());
                continue;
            }

            if (todoManager.hasItem(todo)) {
                logger.info(String.format(MESSAGE_DUPLICATE_FOUND, jsonAdaptedTodo));
                continue;
            }
            todoManager.addItem(todo);
        }
        return todoManager;
    }

}
