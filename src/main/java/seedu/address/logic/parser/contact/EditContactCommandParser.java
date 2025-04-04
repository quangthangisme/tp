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

    private static final PrefixAlias ID_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_ID;
    private static final PrefixAlias NAME_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_NAME;
    private static final PrefixAlias EMAIL_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_EMAIL;
    private static final PrefixAlias TAG_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_TAG;
    private static final PrefixAlias COURSE_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_COURSE;
    private static final PrefixAlias GROUP_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_GROUP;

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = tokenizeArgs(args);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        EditContactDescriptor editContactDescriptor = buildEditContactDescriptor(argMultimap);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor);
    }

    private ArgumentMultimap tokenizeArgs(String args) throws ParseException {
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(ID_PREFIX, NAME_PREFIX, EMAIL_PREFIX, TAG_PREFIX, COURSE_PREFIX, GROUP_PREFIX)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        return argMultimap;
    }

    private EditContactDescriptor buildEditContactDescriptor(ArgumentMultimap argMultimap)
            throws ParseException {
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(ID_PREFIX).isPresent()) {
            editContactDescriptor.setId(ContactParserUtil.parseId(argMultimap.getValue(ID_PREFIX).get()));
        }
        if (argMultimap.getValue(NAME_PREFIX).isPresent()) {
            editContactDescriptor.setName(
                    ContactParserUtil.parseName(argMultimap.getValue(NAME_PREFIX).get()));
        }
        if (argMultimap.getValue(EMAIL_PREFIX).isPresent()) {
            editContactDescriptor.setEmail(
                    ContactParserUtil.parseEmail(argMultimap.getValue(EMAIL_PREFIX).get()));
        }
        if (argMultimap.getValue(COURSE_PREFIX).isPresent()) {
            editContactDescriptor.setCourse(
                    ContactParserUtil.parseCourse(argMultimap.getValue(COURSE_PREFIX).get()));
        }
        if (argMultimap.getValue(GROUP_PREFIX).isPresent()) {
            editContactDescriptor.setGroup(
                    ContactParserUtil.parseGroup(argMultimap.getValue(GROUP_PREFIX).get()));
        }
        if (argMultimap.getValue(TAG_PREFIX).isPresent()) {
            editContactDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(TAG_PREFIX).get()));
        }

        return editContactDescriptor;
    }

}
