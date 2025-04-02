package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.RemoveTagFromEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Tag;

/**
 * Parses input arguments and creates a new RemoveTagFromEventCommand object.
 */
public class RemoveTagFromEventCommandParser implements Parser<RemoveTagFromEventCommand> {
    @Override
    public RemoveTagFromEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        TagPrefix tagPrefix = new TagPrefix();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, tagPrefix.getAll());
        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(tagPrefix.getAll());
        if (argMultimap.getValue(tagPrefix).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagFromEventCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get());
        if (tags.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_TAG);
        }
        return new RemoveTagFromEventCommand(index, tags);
    }
}
