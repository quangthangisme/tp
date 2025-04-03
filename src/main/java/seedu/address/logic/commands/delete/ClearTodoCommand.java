package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerAndList;

/**
 * Clears the todo list.
 */
public class ClearTodoCommand extends ClearCommand<TodoManagerAndList, Todo> {

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Clears the todo list.";
    public static final String MESSAGE_SUCCESS = "Todo list has been cleared!";

    /**
     * Creates a {@code ClearTodoCommand} to clear the todo list in the Model.
     */
    public ClearTodoCommand() {
        super(Model::getTodoManagerAndList);
    }

    @Override
    public void clear(TodoManagerAndList managerAndList) {
        managerAndList.setItemManager(new TodoManager());
    }

    @Override
    public void cascade(Model model) {
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
