package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.logic.commands.update.MarkTodoAsNotDoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.TodoStatus;

/**
 * Parses input arguments and creates a new MarkTodoAsDoneCommand object
 */
public class MarkTodoAsNotDoneCommandParser implements Parser<MarkTodoAsNotDoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkTodoAsNotDoneCommand
     * and returns a MarkTodoAsNotDoneCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkTodoAsNotDoneCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index = ParserUtil.parseIndex(args);

        EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();
        editTodoDescriptor.setStatus(new TodoStatus(false));
        return new MarkTodoAsNotDoneCommand(index, editTodoDescriptor);
    }

}
