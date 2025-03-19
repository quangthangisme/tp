package seedu.address.logic.commands.todo;

import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;

/**
 * Mark a todo as done.
 */
public class MarkTodoAsDoneCommand extends EditCommand<Todo> {

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
    public MarkTodoAsDoneCommand(Index index) {
        super(index, Model::getTodoManagerAndList);
    }

    @Override
    public Todo createEditedItem(Model model, Todo todoToEdit) throws CommandException {
        if (todoToEdit.getStatus().isDone()) {
            throw new CommandException(MESSAGE_TODO_ALREADY_DONE);
        }

        return new Todo(
                todoToEdit.getName(),
                todoToEdit.getDeadline(),
                todoToEdit.getLocation(),
                new TodoStatus(true),
                todoToEdit.getPersons()
        );
    }

    @Override
    public String getInvalidIndexMessage() {
        return TodoMessages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return TodoMessages.MESSAGE_DUPLICATE_TODO;
    }

    @Override
    public String getSuccessMessage(Todo todo) {
        return String.format(MESSAGE_TODO_MARKED_DONE, Messages.format(todo));
    }
}
