package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;

/**
 * Edits the details of an existing todo in the address book.
 */
public class EditTodoCommand extends EditCommand<Todo> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the todo identified by the index number used in the "
            + "displayed todo list. "
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
    public static final String MESSAGE_DUPLICATE_TODO =
            "This todo already exists in the address book.";

    protected final EditTodoDescriptor editTodoDescriptor;
    private final Optional<List<Index>> linkedContactIndicesOpt;

    /**
     * @param index              of the todo in the filtered todo list to edit
     * @param editTodoDescriptor details to edit the todo with
     */
    public EditTodoCommand(Index index, EditTodoDescriptor editTodoDescriptor) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(editTodoDescriptor);
        this.editTodoDescriptor = new EditTodoDescriptor(editTodoDescriptor);
        this.linkedContactIndicesOpt = Optional.empty();
    }

    /**
     * @param index                of the todo in the filtered todo list to edit
     * @param editTodoDescriptor   details to edit the todo with
     * @param linkedContactIndices indices of contacts to link with
     */
    public EditTodoCommand(Index index, EditTodoDescriptor editTodoDescriptor,
                           List<Index> linkedContactIndices) {
        super(index, Model::getTodoManagerAndList);
        requireNonNull(editTodoDescriptor);
        this.editTodoDescriptor = new EditTodoDescriptor(editTodoDescriptor);
        this.linkedContactIndicesOpt = Optional.of(List.copyOf(linkedContactIndices));
    }

    /**
     * Creates and returns a {@code Todo} with the details of {@code todoToEdit} edited with
     * {@code editTodoDescriptor}.
     */
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        assert todoToEdit != null;

        if (linkedContactIndicesOpt.isPresent()) {
            List<Index> linkedContactIndices = linkedContactIndicesOpt.get();
            List<Contact> filteredContacts =
                    model.getContactManagerAndList().getFilteredItemsList();
            for (Index index : linkedContactIndices) {
                if (index.getZeroBased() >= filteredContacts.size()) {
                    throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_RANGE_CONTACT,
                            index.getOneBased()));
                }
            }
            List<Contact> contacts = linkedContactIndices.stream().map(Index::getZeroBased)
                    .map(filteredContacts::get).toList();
            editTodoDescriptor.setContacts(contacts);
        }

        Name updatedName = editTodoDescriptor.getName().orElse(todoToEdit.getName());
        Datetime updatedDeadline =
                editTodoDescriptor.getDeadline().orElse(todoToEdit.getDeadline());
        Location updatedLocation =
                editTodoDescriptor.getLocation().orElse(todoToEdit.getLocation());
        TodoStatus updatedStatus = editTodoDescriptor.getStatus().orElse(todoToEdit.getStatus());
        List<Contact> updatedContacts =
                editTodoDescriptor.getContacts().orElse(todoToEdit.getContacts());
        Set<Tag> updatedTags = editTodoDescriptor.getTags().orElse(todoToEdit.getTags());

        return new Todo(updatedName, updatedDeadline, updatedLocation, updatedStatus,
                updatedContacts, updatedTags);
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
