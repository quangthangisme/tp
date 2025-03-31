package seedu.address.logic.commands.read;

import static seedu.address.logic.TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Displays information of a todo identified using its displayed index.
 */
public class InfoTodoCommand extends InfoCommand<Todo> {

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Displays the complete information belonging to the todo"
            + " identified by the index number used in the displayed todo list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INFO_TODO_SUCCESS = "Displaying full information of todo!\n%1$s";

    /**
     * Creates a {@code InfoTodoCommand} to display information of the {@code Todo} at the specified {@code index}.
     */
    public InfoTodoCommand(Index index) {
        super(index, Model::getTodoManagerAndList);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return MESSAGE_INDEX_OUT_OF_RANGE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo itemToDisplay) {
        return String.format(MESSAGE_INFO_TODO_SUCCESS, Messages.format(itemToDisplay));
    }

}
