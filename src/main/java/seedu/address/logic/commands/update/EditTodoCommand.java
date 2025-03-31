package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;
import seedu.address.model.todo.TodoStatus;

/**
 * Edits the details of an existing todo in the address book.
 */
public class EditTodoCommand extends EditCommand<Todo> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the todo identified by the index number used in the displayed todo list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TODO_NAME_LONG + " NAME] "
            + "[" + PREFIX_TODO_DEADLINE_LONG + " DEADLINE] "
            + "[" + PREFIX_TODO_LOCATION_LONG + " LOCATION] "
            + "[" + PREFIX_TODO_TAG_LONG + " TAG]...\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_NAME_LONG + " Complete project\n";

    public static final String MESSAGE_EDIT_TODO_SUCCESS = "Edited Todo: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists in the address book.";

    protected final EditTodoDescriptor editTodoDescriptor;

    /**
     * @param index              of the todo in the filtered todo list to edit
     * @param editTodoDescriptor details to edit the todo with
     */
    public EditTodoCommand(Index index, EditTodoDescriptor editTodoDescriptor) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(editTodoDescriptor);
        this.editTodoDescriptor = new EditTodoDescriptor(editTodoDescriptor);
    }

    /**
     * Creates and returns a {@code Todo} with the details of {@code todoToEdit} edited with
     * {@code editTodoDescriptor}.
     */
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        assert todoToEdit != null;

        TodoName updatedName = editTodoDescriptor.getName().orElse(todoToEdit.getName());
        TodoDeadline updatedDeadline = editTodoDescriptor.getDeadline().orElse(todoToEdit.getDeadline());
        TodoLocation updatedLocation = editTodoDescriptor.getLocation().orElse(todoToEdit.getLocation());
        TodoStatus updatedStatus = editTodoDescriptor.getStatus().orElse(todoToEdit.getStatus());
        List<Contact> updatedContacts = editTodoDescriptor.getContacts().orElse(todoToEdit.getContacts());
        Set<Tag> updatedTags = editTodoDescriptor.getTags().orElse(todoToEdit.getTags());

        return new Todo(updatedName, updatedDeadline, updatedLocation, updatedStatus, updatedContacts, updatedTags);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo editedItem) {
        return String.format(MESSAGE_EDIT_TODO_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditTodoCommand otherEditCommand)) {
            return false;
        }

        return targetIndex.equals(otherEditCommand.targetIndex)
                && editTodoDescriptor.equals(otherEditCommand.editTodoDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editTodoDescriptor", editTodoDescriptor)
                .toString();
    }

}
