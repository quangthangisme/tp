package seedu.address.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditPersonCommandParser implements Parser<EditPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON_ID, PREFIX_PERSON_NAME,
                PREFIX_PERSON_PHONE, PREFIX_PERSON_EMAIL, PREFIX_PERSON_TAG, PREFIX_PERSON_COURSE, PREFIX_PERSON_GROUP);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditPersonCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_ID, PREFIX_PERSON_NAME, PREFIX_PERSON_PHONE,
                PREFIX_PERSON_EMAIL, PREFIX_PERSON_COURSE, PREFIX_PERSON_GROUP);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_PERSON_ID).isPresent()) {
            editPersonDescriptor.setId(PersonParseUtil.parseId(argMultimap.getValue(PREFIX_PERSON_ID).get()));
        }
        if (argMultimap.getValue(PREFIX_PERSON_NAME).isPresent()) {
            editPersonDescriptor.setName(PersonParseUtil.parseName(argMultimap.getValue(PREFIX_PERSON_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PERSON_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(PersonParseUtil.parsePhone(argMultimap.getValue(PREFIX_PERSON_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_PERSON_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(PersonParseUtil.parseEmail(argMultimap.getValue(PREFIX_PERSON_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_PERSON_COURSE).isPresent()) {
            editPersonDescriptor.setCourse(PersonParseUtil
                    .parseModule(argMultimap.getValue(PREFIX_PERSON_COURSE).get()));
        }
        if (argMultimap.getValue(PREFIX_PERSON_GROUP).isPresent()) {
            editPersonDescriptor.setGroup(PersonParseUtil.parseGroup(argMultimap.getValue(PREFIX_PERSON_GROUP).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_PERSON_TAG)).ifPresent(editPersonDescriptor::setTags);

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPersonCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPersonCommand(index, editPersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet()
                : tags;
        return Optional.of(PersonParseUtil.parseTags(tagSet));
    }

}
