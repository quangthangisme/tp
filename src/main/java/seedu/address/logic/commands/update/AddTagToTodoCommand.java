package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;

/**
 * Adds a tag to a specified todo
 */
public class AddTagToTodoCommand extends EditTodoCommand {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified todo.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_TAG_LONG + " <tag>\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_TAG_LONG + " important ";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to todo: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag is already assigned to this todo";

    private final Set<Tag> newTags;

    /**
     * Creates a AddTagToTodoCommand to add tags to a todo at a specific index.
     */
    public AddTagToTodoCommand(Index index, Set<Tag> newTags) {
        super(index, new EditTodoDescriptor());
        requireNonNull(newTags);
        this.newTags = newTags;
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        requireNonNull(todoToEdit);
        // Every tag that is intended to be added must not already be in the contact
        Set<Tag> existingTags = todoToEdit.getTags();
        for (Tag tag : newTags) {
            if (existingTags.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
            }
        }

        // Merge tags and update
        Set<Tag> combinedTags = new HashSet<>(existingTags);
        combinedTags.addAll(newTags);
        editTodoDescriptor.setTags(combinedTags);

        return super.createEditedItem(model, todoToEdit);
    }

    @Override
    public String getSuccessMessage(Todo editedItem) {
        return String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedItem));
    }

}
