package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditTodoCommand;
import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
import seedu.address.logic.parser.event.EventCliAlias;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.TodoStatus;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditTodoCommandParser implements Parser<EditTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        PrefixAlias namePrefix = TodoCliAlias.TODO_NAME_PREFIX_ALIAS;
        PrefixAlias deadlinePrefix = TodoCliAlias.TODO_DEADLINE_PREFIX_ALIAS;
        PrefixAlias locationPrefix = TodoCliAlias.TODO_LOCATION_PREFIX_ALIAS;
        PrefixAlias tagPrefix = TodoCliAlias.TODO_TAG_PREFIX_ALIAS;
        PrefixAlias contactPrefix = TodoCliAlias.TODO_LINKED_CONTACT_PREFIX_ALIAS;
        PrefixAlias statusPrefix = TodoCliAlias.TODO_STATUS_ALIAS;
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(namePrefix, deadlinePrefix, locationPrefix, tagPrefix, contactPrefix, statusPrefix)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTodoCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();

        if (argMultimap.getValue(namePrefix).isPresent()) {
            editTodoDescriptor.setName(TodoParserUtil.parseName(argMultimap.getValue(namePrefix).get()));
        }
        if (argMultimap.getValue(deadlinePrefix).isPresent()) {
            editTodoDescriptor.setDeadline(TodoParserUtil.parseDeadline(argMultimap.getValue(deadlinePrefix).get()));
        }
        if (argMultimap.getValue(locationPrefix).isPresent()) {
            editTodoDescriptor.setLocation(
                    TodoParserUtil.parseLocation(argMultimap.getValue(locationPrefix).get()));
        }
        if (argMultimap.getValue(tagPrefix).isPresent()) {
            editTodoDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get()));
        }
        if (argMultimap.getValue(statusPrefix).isPresent()) {
            editTodoDescriptor.setStatus(new TodoStatus(
                    ParserUtil.parseBoolean(argMultimap.getValue(statusPrefix).get())));
        }
        Optional<List<Index>> linkedContactIndices = Optional.empty();
        PrefixAlias eventContact = EventCliAlias.EVENT_LINKED_CONTACT_PREFIX_ALIAS;
        if (argMultimap.getValue(eventContact).isPresent()) {
            linkedContactIndices = Optional.of(
                    ParserUtil.parseIndices(argMultimap.getValue(eventContact).get()));
            editTodoDescriptor.setContacts(List.of());
        }

        if (!editTodoDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTodoCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTodoCommand(index, editTodoDescriptor, linkedContactIndices);
    }

}
