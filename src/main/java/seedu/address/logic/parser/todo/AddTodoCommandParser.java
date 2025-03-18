package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.todo.AddTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;

/**
 * Parses input arguments and creates a new AddTodoCommand object.
 */
public class AddTodoCommandParser implements Parser<AddTodoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTodoCommand and returns
     * an AddTodoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddTodoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME,
                PREFIX_TODO_DEADLINE, PREFIX_TODO_LOCATION);

        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_NAME, PREFIX_TODO_DEADLINE,
                PREFIX_TODO_LOCATION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTodoCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME, PREFIX_TODO_DEADLINE,
                PREFIX_TODO_LOCATION);
        TodoName name = TodoParseUtil.parseName(argMultimap.getValue(PREFIX_TODO_NAME).get());
        TodoDeadline deadline =
                TodoParseUtil.parseDeadline(argMultimap.getValue(PREFIX_TODO_DEADLINE).get());
        TodoLocation location =
                TodoParseUtil.parseLocation(argMultimap.getValue(PREFIX_TODO_LOCATION).get());

        Todo todo = new Todo(name, deadline, location);

        return new AddTodoCommand(todo);
    }
}
