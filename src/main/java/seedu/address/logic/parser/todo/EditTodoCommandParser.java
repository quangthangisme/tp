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
import seedu.address.logic.parser.event.EventCliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.TodoStatus;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditTodoCommandParser implements Parser<EditTodoCommand> {

    private static final PrefixAlias NAME_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_NAME;
    private static final PrefixAlias DEADLINE_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_DEADLINE;
    private static final PrefixAlias LOCATION_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_LOCATION;
    private static final PrefixAlias TAG_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_TAG;
    private static final PrefixAlias CONTACT_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_LINKED_CONTACT;
    private static final PrefixAlias STATUS_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_STATUS;

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = tokenizeArgs(args);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        EditTodoDescriptor editTodoDescriptor = buildEditTodoDescriptor(argMultimap);
        Optional<List<Index>> linkedContactIndices = parseLinkedContacts(argMultimap, editTodoDescriptor);

        if (!editTodoDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTodoCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTodoCommand(index, editTodoDescriptor, linkedContactIndices);
    }

    private ArgumentMultimap tokenizeArgs(String args) throws ParseException {
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(NAME_PREFIX, DEADLINE_PREFIX, LOCATION_PREFIX, TAG_PREFIX, CONTACT_PREFIX, STATUS_PREFIX)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTodoCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        return argMultimap;
    }

    private EditTodoDescriptor buildEditTodoDescriptor(ArgumentMultimap argMultimap)
            throws ParseException {
        EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();

        if (argMultimap.getValue(NAME_PREFIX).isPresent()) {
            editTodoDescriptor.setName(TodoParserUtil.parseName(argMultimap.getValue(NAME_PREFIX).get()));
        }
        if (argMultimap.getValue(DEADLINE_PREFIX).isPresent()) {
            editTodoDescriptor.setDeadline(TodoParserUtil.parseDeadline(argMultimap.getValue(DEADLINE_PREFIX).get()));
        }
        if (argMultimap.getValue(LOCATION_PREFIX).isPresent()) {
            editTodoDescriptor.setLocation(
                    TodoParserUtil.parseLocation(argMultimap.getValue(LOCATION_PREFIX).get()));
        }
        if (argMultimap.getValue(TAG_PREFIX).isPresent()) {
            editTodoDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(TAG_PREFIX).get()));
        }
        if (argMultimap.getValue(STATUS_PREFIX).isPresent()) {
            editTodoDescriptor.setStatus(new TodoStatus(
                    ParserUtil.parseBoolean(argMultimap.getValue(STATUS_PREFIX).get())));
        }

        return editTodoDescriptor;
    }

    private Optional<List<Index>> parseLinkedContacts(ArgumentMultimap argMultimap,
                                                      EditTodoDescriptor editTodoDescriptor)
            throws ParseException {
        PrefixAlias eventContact = EventCliSyntax.PREFIX_ALIAS_EVENT_LINKED_CONTACT;
        if (argMultimap.getValue(eventContact).isPresent()) {
            Optional<List<Index>> linkedContactIndices = Optional.of(
                    ParserUtil.parseIndices(argMultimap.getValue(eventContact).get()));
            editTodoDescriptor.setContacts(List.of());
            return linkedContactIndices;
        }
        return Optional.empty();
    }

}
