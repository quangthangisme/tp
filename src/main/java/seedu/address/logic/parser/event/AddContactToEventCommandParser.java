package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_CONTACT_INDEX;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.AddContactToEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_LINKED_CONTACT_LONG);

        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_LINKED_CONTACT_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_EVENT_LINKED_CONTACT_LONG) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactToEventCommand.MESSAGE_USAGE));
        }

        // Parse index of event to edit
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        // Parse contact indices, duplicates are handled in parseIndices
        List<Index> contactIndices = ParserUtil.parseIndices(
                argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).get());

        // Check against empty and duplicate contact indices
        if (contactIndices.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_CONTACT_INDEX);
        }

        return new AddContactToEventCommand(index, contactIndices);
    }
}
