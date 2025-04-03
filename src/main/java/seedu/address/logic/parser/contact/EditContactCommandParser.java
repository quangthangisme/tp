package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditContactCommand;
import seedu.address.logic.commands.update.EditContactDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditContactCommandParser implements Parser<EditContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        PrefixAlias idPrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_ID;
        PrefixAlias namePrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_NAME;
        PrefixAlias emailPrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_EMAIL;
        PrefixAlias tagPrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_TAG;
        PrefixAlias coursePrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_COURSE;
        PrefixAlias groupPrefix = ContactCliSyntax.PREFIX_ALIAS_CONTACT_GROUP;
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(idPrefix, namePrefix, emailPrefix, tagPrefix, coursePrefix, groupPrefix)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(idPrefix).isPresent()) {
            editContactDescriptor.setId(ContactParserUtil.parseId(argMultimap.getValue(idPrefix).get()));
        }
        if (argMultimap.getValue(namePrefix).isPresent()) {
            editContactDescriptor.setName(
                    ContactParserUtil.parseName(argMultimap.getValue(namePrefix).get()));
        }
        if (argMultimap.getValue(emailPrefix).isPresent()) {
            editContactDescriptor.setEmail(
                    ContactParserUtil.parseEmail(argMultimap.getValue(emailPrefix).get()));
        }
        if (argMultimap.getValue(coursePrefix).isPresent()) {
            editContactDescriptor.setCourse(
                    ContactParserUtil.parseCourse(argMultimap.getValue(coursePrefix).get()));
        }
        if (argMultimap.getValue(groupPrefix).isPresent()) {
            editContactDescriptor.setGroup(
                    ContactParserUtil.parseGroup(argMultimap.getValue(groupPrefix).get()));
        }
        if (argMultimap.getValue(tagPrefix).isPresent()) {
            editContactDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get()));
        }

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor);
    }

}
