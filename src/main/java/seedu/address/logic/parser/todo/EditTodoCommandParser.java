package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditTodoCommand;
import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME_LONG,
                PREFIX_TODO_DEADLINE_LONG, PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_TAG_LONG,
                PREFIX_TODO_LINKED_CONTACT_LONG);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTodoCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME_LONG, PREFIX_TODO_DEADLINE_LONG,
                PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_TAG_LONG, PREFIX_TODO_LINKED_CONTACT_LONG);

        EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();

        if (argMultimap.getValue(PREFIX_TODO_NAME_LONG).isPresent()) {
            editTodoDescriptor.setName(TodoParserUtil.parseName(argMultimap.getValue(PREFIX_TODO_NAME_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_TODO_DEADLINE_LONG).isPresent()) {
            editTodoDescriptor.setDeadline(
                    TodoParserUtil.parseDeadline(argMultimap.getValue(PREFIX_TODO_DEADLINE_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_TODO_LOCATION_LONG).isPresent()) {
            editTodoDescriptor.setLocation(
                    TodoParserUtil.parseLocation(argMultimap.getValue(PREFIX_TODO_LOCATION_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_TODO_TAG_LONG).isPresent()) {
            editTodoDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(PREFIX_TODO_TAG_LONG).get()));
        }
        List<Index> linkedContactIndices = List.of();
        if (argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).isPresent()) {
            linkedContactIndices =
                    ParserUtil.parseIndices(argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).get());
            editTodoDescriptor.setContacts(List.of());
        }

        if (!editTodoDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTodoCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTodoCommand(index, editTodoDescriptor, linkedContactIndices);
    }

}
