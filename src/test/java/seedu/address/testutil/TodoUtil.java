package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME;

import seedu.address.logic.commands.todo.AddTodoCommand;
import seedu.address.model.todo.Todo;

/**
 * A utility class for Todo.
 */
public class TodoUtil {

    /**
     * Returns an add command string for adding the {@code todo}.
     */
    public static String getAddCommand(Todo todo) {
        return TODO_COMMAND_WORD + " " + AddTodoCommand.COMMAND_WORD + " " + getTodoDetails(todo);
    }

    /**
     * Returns the part of command string for the given {@code todo}'s details.
     */
    public static String getTodoDetails(Todo todo) {
        String sb = PREFIX_TODO_NAME + todo.getName().name + " "
                + PREFIX_TODO_DEADLINE + todo.getDeadline().toString() + " "
                + PREFIX_TODO_LOCATION + todo.getLocation().value + " ";
        return sb;
    }
}
