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
 * Remove a tag from a specified todo
 */
public class RemoveTagFromTodoCommand extends EditTodoCommand {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes a tag from a specified todo.\n"
            + "Parameters: INDEX "
            + PREFIX_TODO_TAG_LONG + "TAG(S)\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TODO_TAG_LONG + "important optional";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag from todo: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "The tag is not assigned to this todo";

    private final Set<Tag> newTags;

    /**
     * Creates an RemoveTagFromTodoCommand to remove a tag from the specified {@code Todo}
     */
    public RemoveTagFromTodoCommand(Index index, Set<Tag> tags) {
        super(index, new EditTodoDescriptor());
        requireNonNull(tags);
        this.newTags = tags;
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        requireNonNull(todoToEdit);
        // Every tag that is intended to be removed must already be in the todo
        Set<Tag> existingTags = todoToEdit.getTags();
        for (Tag tag : newTags) {
            if (!existingTags.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND));
            }
        }

        // Merge tags and update
        Set<Tag> combinedTags = new HashSet<>(existingTags);
        combinedTags.removeAll(newTags);
        editTodoDescriptor.setTags(combinedTags);

        return super.createEditedItem(model, todoToEdit);
    }

    @Override
    public String getSuccessMessage(Todo editedTodo) {
        return String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(editedTodo));
    }
}
