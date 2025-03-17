package seedu.address.logic.commands.todo;

import seedu.address.logic.abstractcommand.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Lists all todos to the user.
 */
public class ListTodoCommand extends ListCommand<Todo> {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all todos";
    public static final String MESSAGE_USAGE = "list";

    /**
     * Creates a {@code ListTodoCommand}.
     */
    public ListTodoCommand() {
        super(Model::getTodoManagerAndList);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
