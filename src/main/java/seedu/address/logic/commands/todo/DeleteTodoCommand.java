package seedu.address.logic.commands.todo;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.DeleteCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Deletes a todo identified using its displayed index.
 */
public class DeleteTodoCommand extends DeleteCommand<Todo> {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the todo identified by the index number used in the displayed todo list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INVALID_TODO_DISPLAYED_INDEX =
            "The todo index provided is invalid";
    public static final String MESSAGE_DELETE_TODO_SUCCESS = "Deleted todo: %1$s";

    /**
     * Creates a {@code DeleteTodoCommand} to delete a {@code Todo} at the specified
     * {@code targetIndex}.
     *
     * @throws NullPointerException if {@code targetIndex} is {@code null}.
     */
    public DeleteTodoCommand(Index targetIndex) {
        super(targetIndex, Model::getTodoManagerAndList);
    }

    @Override
    public String getInvalidIndexMessage() {
        return MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_DELETE_TODO_SUCCESS, Messages.format(todo));
    }
}
