package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.todo.Todo;

/**
 * Associate a list of contacts to a todo.
 */
public class AddContactToTodoCommand extends EditTodoCommand {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Associate a todo with some contacts.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_LINKED_CONTACT_LONG + "[CONTACT_INDEX/INDICES]\n"
            + "INDEX must be a positive integer.\n"
            + "CONTACT_INDEX is based on the list displayed on the right.\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_LINKED_CONTACT_LONG + "1 3 4";

    public static final String MESSAGE_ADD_CONTACT_SUCCESS = "Added contacts to todo: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT =
            "Contact %1$s is already assigned to this todo.";

    private final List<Index> contactIndices;

    /**
     * Creates an AddContactToTodoCommand to add the contacts at the specified {@code contactIndices}
     * to the todo at the specified {@code index}.
     */
    public AddContactToTodoCommand(Index index, List<Index> contactIndices) {
        super(index, new EditTodoDescriptor());
        requireNonNull(contactIndices);
        this.contactIndices = contactIndices;
    }

    @Override
    public Todo createEditedItem(Model model, Todo itemToEdit) throws CommandException {
        // Check indices against current filtered list
        List<Contact> filteredContacts = model.getContactManagerAndList().getFilteredItemsList();
        for (Index index : contactIndices) {
            if (index.getZeroBased() >= filteredContacts.size()) {
                throw new CommandException(String.format(MESSAGE_INDEX_OUT_OF_RANGE_CONTACT, index.getOneBased()));
            }
        }

        // Map to contacts and check for duplicates
        List<Contact> addedContacts = contactIndices.stream()
                .map(index -> filteredContacts.get(index.getZeroBased())).toList();
        for (Contact contact : addedContacts) {
            if (itemToEdit.getContacts().contains(contact)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_CONTACT,
                        Messages.getSimplifiedFormat(contact)));
            }
        }

        // Update the descriptor and call super
        List<Contact> combinedContacts =
                Stream.concat(itemToEdit.getContacts().stream(), addedContacts.stream()).toList();
        editTodoDescriptor.setContacts(combinedContacts);
        return super.createEditedItem(model, itemToEdit);
    }

    @Override
    public String getSuccessMessage(Todo editedItem) {
        return String.format(MESSAGE_ADD_CONTACT_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddContactToTodoCommand otherCommand)) {
            return false;
        }

        return targetIndex.equals(otherCommand.targetIndex)
                && contactIndices.equals(otherCommand.contactIndices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetIndex, contactIndices);
    }
}
