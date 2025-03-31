package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditContactDescriptor;
import seedu.address.logic.commands.update.RemoveTagFromContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Tag;

/**
 * Parses input arguments and creates a new RemoveTagFromContactCommand object
 */
public class RemoveTagFromContactCommandParser implements Parser<RemoveTagFromContactCommand> {
    @Override
    public RemoveTagFromContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT_TAG_LONG);

        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONTACT_TAG_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_CONTACT_TAG_LONG) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagFromContactCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagFromContactCommand.MESSAGE_USAGE), pe);
        }

        Set<Tag> tags = ContactParserUtil.parseTags(argMultimap.getAllValues(PREFIX_CONTACT_TAG_LONG));
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        editContactDescriptor.setTags(tags);

        return new RemoveTagFromContactCommand(index, editContactDescriptor);
    }
}
