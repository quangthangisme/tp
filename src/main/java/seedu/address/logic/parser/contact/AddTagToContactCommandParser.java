package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_TAG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.AddTagToContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Tag;

/**
 * Parses input arguments and creates a new AddTagToContactCommand object
 */
public class AddTagToContactCommandParser implements Parser<AddTagToContactCommand> {
    @Override
    public AddTagToContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        PrefixAlias tagPrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_TAG;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, tagPrefix.getAll());

        // Ensure only one prefix is present
        argMultimap.verifyNoDuplicatePrefixesFor(tagPrefix.getAll());
        if (!argMultimap.arePrefixAliasPresent(tagPrefix) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagToContactCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get());
        if (tags.isEmpty()) {
            throw new ParseException(MESSAGE_MISSING_TAG);
        }
        return new AddTagToContactCommand(index, tags);
    }
}
