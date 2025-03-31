package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.AddTagToEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.contact.ContactParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddTagToEventCommandParser object.
 */
public class AddTagToEventCommandParser implements Parser<AddTagToEventCommand> {
    @Override
    public AddTagToEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_TAG_LONG);

        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_TAG_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_EVENT_TAG_LONG) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagToEventCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        // It is guaranteed that there is only one --tag.
        // Get the sole value, split by whitespace
        // convert to collection<String>, then parse to Set<Tag>
        Collection<String> tags = argMultimap.getValue(PREFIX_CONTACT_TAG_LONG)
                .map(s -> Arrays.asList(s.split("\\s+")))
                .orElse(Collections.emptyList());

        return new AddTagToEventCommand(index, ContactParserUtil.parseTags(tags));
    }
}
