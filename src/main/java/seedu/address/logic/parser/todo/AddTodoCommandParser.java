package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
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
        PrefixAlias namePrefix = TodoCliAlias.TODO_NAME_PREFIX_ALIAS;
        PrefixAlias deadlinePrefix = TodoCliAlias.TODO_DEADLINE_PREFIX_ALIAS;
        PrefixAlias locationPrefix = TodoCliAlias.TODO_LOCATION_PREFIX_ALIAS;
        PrefixAlias tagPrefix = TodoCliAlias.TODO_TAG_PREFIX_ALIAS;
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(namePrefix, deadlinePrefix, locationPrefix, tagPrefix)
                .toArray();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        if (argMultimap.getValue(namePrefix).isEmpty()
                || argMultimap.getValue(deadlinePrefix).isEmpty()
                || argMultimap.getValue(locationPrefix).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTodoCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);
        Name name = TodoParserUtil.parseName(argMultimap.getValue(namePrefix).get());
        Datetime deadline = TodoParserUtil.parseDeadline(argMultimap.getValue(deadlinePrefix).get());
        Location location = TodoParserUtil.parseLocation(argMultimap.getValue(locationPrefix).get());
        Set<Tag> tagSet = new HashSet<>();
        if (argMultimap.arePrefixAliasPresent(tagPrefix)) {
            tagSet = ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get());
        }

        Todo todo = new Todo(name, deadline, location, tagSet);

        return new AddTodoCommand(todo);
    }
}
