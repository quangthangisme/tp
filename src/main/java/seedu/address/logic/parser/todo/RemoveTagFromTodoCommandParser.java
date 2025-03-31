package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.logic.commands.update.RemoveTagFromTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.contact.ContactParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Tag;

/**
 * Parses input arguments and creates a new RemoveTagFromTodoCommand object.
 */
public class RemoveTagFromTodoCommandParser implements Parser<RemoveTagFromTodoCommand> {
    @Override
    public RemoveTagFromTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_TAG_LONG);

        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_TAG_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_TODO_TAG_LONG) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagFromTodoCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagFromTodoCommand.MESSAGE_USAGE), pe);
        }

        Set<Tag> tags = ContactParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TODO_TAG_LONG));
        EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();
        editTodoDescriptor.setTags(tags);

        return new RemoveTagFromTodoCommand(index, editTodoDescriptor);
    }
}
