package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.todo.MarkTodoAsNotDoneCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkTodoAsNotDoneCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkTodoAsNotDoneCommand.MESSAGE_USAGE), pe);
        }
    }

}

