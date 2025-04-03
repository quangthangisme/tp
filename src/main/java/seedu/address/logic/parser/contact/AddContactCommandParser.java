package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.create.AddContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddContactCommandParser implements Parser<AddContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an
     * AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddContactCommand parse(String args) throws ParseException {
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

        // Check that all required prefixes are present
        if (!argMultimap.areAllPrefixAliasPresent(idPrefix, namePrefix, emailPrefix, coursePrefix, groupPrefix)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.MESSAGE_USAGE));
        }

        // Check that there are no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        // getValue further ensures that only short or long prefixes but not both is present
        Id id = ContactParserUtil.parseId(argMultimap.getValue(idPrefix).get());
        Name name = ContactParserUtil.parseName(argMultimap.getValue(namePrefix).get());
        Email email = ContactParserUtil.parseEmail(argMultimap.getValue(emailPrefix).get());
        Course course = ContactParserUtil.parseCourse(argMultimap.getValue(coursePrefix).get());
        Group group = ContactParserUtil.parseGroup(argMultimap.getValue(groupPrefix).get());
        Set<Tag> tagSet = new HashSet<>();
        if (argMultimap.arePrefixAliasPresent(tagPrefix)) {
            tagSet = ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get());
        }

        Contact contact = new Contact(id, name, email, course, group, tagSet);
        return new AddContactCommand(contact);
    }
}
