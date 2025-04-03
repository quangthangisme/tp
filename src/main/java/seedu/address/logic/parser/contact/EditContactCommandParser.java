package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditContactCommand;
import seedu.address.logic.commands.update.EditContactDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
        ArgumentMultimap argMultimap = tokenizeArgs(args);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        EditContactDescriptor editContactDescriptor = buildEditContactDescriptor(argMultimap);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditContactCommand.MESSAGE_NOT_EDITED);
        }

        return new EditContactCommand(index, editContactDescriptor);
    }

    private ArgumentMultimap tokenizeArgs(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_CONTACT_ID_LONG, PREFIX_CONTACT_NAME_LONG, PREFIX_CONTACT_EMAIL_LONG,
                PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_GROUP_LONG, PREFIX_CONTACT_TAG_LONG);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditContactCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONTACT_ID_LONG, PREFIX_CONTACT_NAME_LONG,
                PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_GROUP_LONG,
                PREFIX_CONTACT_TAG_LONG);

        return argMultimap;
    }

    private EditContactDescriptor buildEditContactDescriptor(ArgumentMultimap argMultimap)
            throws ParseException {
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(PREFIX_CONTACT_ID_LONG).isPresent()) {
            editContactDescriptor.setId(ContactParserUtil.parseId(argMultimap.getValue(PREFIX_CONTACT_ID_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_NAME_LONG).isPresent()) {
            editContactDescriptor.setName(
                    ContactParserUtil.parseName(argMultimap.getValue(PREFIX_CONTACT_NAME_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_EMAIL_LONG).isPresent()) {
            editContactDescriptor.setEmail(
                    ContactParserUtil.parseEmail(argMultimap.getValue(PREFIX_CONTACT_EMAIL_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_COURSE_LONG).isPresent()) {
            editContactDescriptor.setCourse(
                    ContactParserUtil.parseCourse(argMultimap.getValue(PREFIX_CONTACT_COURSE_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_GROUP_LONG).isPresent()) {
            editContactDescriptor.setGroup(
                    ContactParserUtil.parseGroup(argMultimap.getValue(PREFIX_CONTACT_GROUP_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_CONTACT_TAG_LONG).isPresent()) {
            editContactDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(PREFIX_CONTACT_TAG_LONG).get()));
        }

        return editContactDescriptor;
    }

}
