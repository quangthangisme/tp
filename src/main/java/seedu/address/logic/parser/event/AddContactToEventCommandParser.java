package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_CONTACT_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.AddContactToEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddContactToEventCommandParser object
 */
public class AddContactToEventCommandParser implements Parser<AddContactToEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddContactToEventCommandParser and returns a AddContactToEventCommand object for
     * execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddContactToEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        PrefixAlias contactPrefix = EventCliAlias.EVENT_LINKED_CONTACT_PREFIX_ALIAS;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, contactPrefix.getAll());

        // Ensure only one prefix is present
        if (argMultimap.getValue(contactPrefix).isEmpty() || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactToEventCommand.MESSAGE_USAGE));
        }
        // Parse index of event to edit
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        // Parse contact indices, duplicates are handled in parseIndices
        String contactRaw = argMultimap.getValue(contactPrefix)
                .orElseThrow(() -> new ParseException(MESSAGE_MISSING_CONTACT_INDEX));
        List<Index> contactIndices = ParserUtil.parseIndices(contactRaw);
        if (contactIndices.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_CONTACT_INDEX);
        }

        return new AddContactToEventCommand(index, contactIndices);
    }
}
