package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.RemoveTagFromTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Tag;

/**
 * Parses input arguments and creates a new RemoveTagFromTodoCommand object.
 */
public class RemoveTagFromTodoCommandParser implements Parser<RemoveTagFromTodoCommand> {
    @Override
    public RemoveTagFromTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        PrefixAlias tagAlias = TodoCliSyntax.PREFIX_ALIAS_TODO_TAG;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, tagAlias.getAll());

        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(tagAlias.getAll());
        if (!argMultimap.arePrefixAliasPresent(tagAlias) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagFromTodoCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getValue(tagAlias).get());
        if (tags.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_TAG);
        }
        return new RemoveTagFromTodoCommand(index, tags);
    }
}
