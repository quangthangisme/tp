package seedu.address.logic.commands.todo;

import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Adds a todo.
 */
public class AddTodoCommand extends AddCommand<Todo> {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists";

    /**
     * Creates an AddTodoCommand to add the specified {@code todo}.
     */
    public AddTodoCommand(Todo todo) {
        super(todo, Model::getTodoManagerAndList);
    }

    @Override
    public String getDuplicateItemMessage() {
        return MESSAGE_DUPLICATE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_SUCCESS, Messages.format(todo));
    }
}
