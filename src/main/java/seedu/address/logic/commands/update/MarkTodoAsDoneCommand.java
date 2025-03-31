package seedu.address.logic.commands.update;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Mark a todo as done.
 */
public class MarkTodoAsDoneCommand extends EditTodoCommand {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Mark a todo as done.\n"
            + "Parameters: INDEX\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_TODO_ALREADY_DONE = "This todo is already marked as done";
    public static final String MESSAGE_TODO_MARKED_DONE = "Marked as done: %1$s";

    /**
     * Creates an {@code MarkTodoAsDoneCommand} to mark a todo as done at the specified {@code
     * index}.
     *
     * @throws NullPointerException if {@code index} is {@code null}.
     */
    public MarkTodoAsDoneCommand(Index index, EditTodoDescriptor descriptor) {
        super(index, descriptor);
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        if (todoToEdit.getStatus().isDone()) {
            throw new CommandException(MESSAGE_TODO_ALREADY_DONE);
        }
        return super.createEditedItem(model, todoToEdit);
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_TODO_MARKED_DONE, Messages.format(todo));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkTodoAsDoneCommand otherCommand)) {
            return false;
        }

        return targetIndex.equals(otherCommand.targetIndex);
    }
}
