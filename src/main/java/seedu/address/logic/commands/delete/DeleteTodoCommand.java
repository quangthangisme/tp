package seedu.address.logic.commands.delete;

import static seedu.address.logic.TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Deletes a todo identified using its displayed index.
 */
public class DeleteTodoCommand extends DeleteCommand<Todo> {

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the todo identified by the index number used in the displayed todo list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TODO_SUCCESS = "Deleted todo: %1$s";

    /**
     * Creates a DeleteTodoCommand to delete the todo at the specified {@code targetIndex}
     */
    public DeleteTodoCommand(Index targetIndex) {
        super(targetIndex, Model::getTodoManagerAndList);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return MESSAGE_INDEX_OUT_OF_RANGE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_DELETE_TODO_SUCCESS, Messages.format(todo));
    }

}
