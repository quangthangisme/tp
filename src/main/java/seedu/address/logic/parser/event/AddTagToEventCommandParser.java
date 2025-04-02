package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.AddTagToEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Tag;

/**
 * Parses input arguments and creates a new AddTagToEventCommandParser object.
 */
public class AddTagToEventCommandParser implements Parser<AddTagToEventCommand> {
    @Override
    public AddTagToEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        TagPrefix tagPrefix = new TagPrefix(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, tagPrefix.getAll());
        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(tagPrefix.getAll());
        if (tagPrefix.getValue(argMultimap).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagToEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        Set<Tag> tags = ParserUtil.parseTags(tagPrefix.getValue(argMultimap).get());
        if (tags.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_TAG);
        }
        return new AddTagToEventCommand(index, tags);
    }
}
