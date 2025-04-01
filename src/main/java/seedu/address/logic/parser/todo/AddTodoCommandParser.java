package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME_LONG,
                PREFIX_TODO_DEADLINE_LONG, PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_TAG_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_NAME_LONG, PREFIX_TODO_DEADLINE_LONG,
                PREFIX_TODO_LOCATION_LONG) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTodoCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME_LONG, PREFIX_TODO_DEADLINE_LONG,
                PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_TAG_LONG);
        Name name = TodoParserUtil.parseName(argMultimap.getValue(PREFIX_TODO_NAME_LONG).get());
        Datetime deadline =
                TodoParserUtil.parseDeadline(argMultimap.getValue(PREFIX_TODO_DEADLINE_LONG).get());
        Location location =
                TodoParserUtil.parseLocation(argMultimap.getValue(PREFIX_TODO_LOCATION_LONG).get());
        Set<Tag> tagSet = new HashSet<>();
        if (argMultimap.arePrefixesPresent(PREFIX_TODO_TAG_LONG)) {
            tagSet = ParserUtil.parseTags(argMultimap.getValue(PREFIX_TODO_TAG_LONG).get());
        }

        Todo todo = new Todo(name, deadline, location, tagSet);

        return new AddTodoCommand(todo);
    }
}
