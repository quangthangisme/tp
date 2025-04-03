package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_COLUMN;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_SHORT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.logic.commands.read.FilterContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ColumnPredicate;
import seedu.address.model.contact.ContactColumn;
import seedu.address.model.contact.ContactPredicate;

/**
 * Parses input arguments and creates a new FilterContactCommand object. Handles complex filter
 * criteria with different operators and values.
 */
public class FilterContactCommandParser implements Parser<FilterContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterContactCommand and
     * returns a FilterContactCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterContactCommand.MESSAGE_USAGE));
        }
        PrefixAlias namePrefix = ContactCliAlias.CONTACT_NAME_PREFIX_ALIAS;
        PrefixAlias emailPrefix = ContactCliAlias.CONTACT_EMAIL_PREFIX_ALIAS;
        PrefixAlias idPrefix = ContactCliAlias.CONTACT_ID_PREFIX_ALIAS;
        PrefixAlias coursePrefix = ContactCliAlias.CONTACT_COURSE_PREFIX_ALIAS;
        PrefixAlias groupPrefix = ContactCliAlias.CONTACT_GROUP_PREFIX_ALIAS;
        PrefixAlias tagPrefix = ContactCliAlias.CONTACT_TAG_PREFIX_ALIAS;
        Prefix[] listOPrefixes = new PrefixAliasListBuilder()
                .add(namePrefix, emailPrefix, idPrefix, coursePrefix, groupPrefix, tagPrefix)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOPrefixes);

        argMultimap.verifyNoDuplicatePrefixesFor(listOPrefixes);

        Map<ContactColumn, ColumnPredicate> filterCriteriaMap = new HashMap<>();

        List<Prefix> allPrefixes = List.of(listOPrefixes);

        parsePrefixes(allPrefixes, argMultimap, filterCriteriaMap);

        if (filterCriteriaMap.isEmpty()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterContactCommand(new ContactPredicate(filterCriteriaMap));
    }


    /**
     * Converts a prefix to its corresponding column.
     *
     * @param prefix the prefix to convert
     * @return the corresponding column
     * @throws ParseException if the prefix does not correspond to a valid column
     */
    private ContactColumn getColumnFromPrefix(Prefix prefix) throws ParseException {
        String prefixStr = prefix.getPrefix();

        if (prefixStr.equals(PREFIX_CONTACT_ID_LONG.getPrefix())
                || prefixStr.equals(PREFIX_CONTACT_ID_SHORT.getPrefix())) {
            return ContactColumn.ID;
        } else if (prefixStr.equals(PREFIX_CONTACT_NAME_LONG.getPrefix())
                || prefixStr.equals(PREFIX_CONTACT_NAME_SHORT.getPrefix())) {
            return ContactColumn.NAME;
        } else if (prefixStr.equals(PREFIX_CONTACT_EMAIL_LONG.getPrefix())
                || prefixStr.equals(PREFIX_CONTACT_EMAIL_SHORT.getPrefix())) {
            return ContactColumn.EMAIL;
        } else if (prefixStr.equals(PREFIX_CONTACT_TAG_LONG.getPrefix())
                || prefixStr.equals(PREFIX_CONTACT_TAG_SHORT.getPrefix())) {
            return ContactColumn.TAG;
        } else if (prefixStr.equals(PREFIX_CONTACT_COURSE_LONG.getPrefix())
                || prefixStr.equals(PREFIX_CONTACT_COURSE_LONG.getPrefix())) {
            return ContactColumn.COURSE;
        } else if (prefixStr.equals(PREFIX_CONTACT_GROUP_LONG.getPrefix())
                || prefixStr.equals(PREFIX_CONTACT_GROUP_SHORT.getPrefix())) {
            return ContactColumn.GROUP;
        } else {
            throw new ParseException(String.format(MESSAGE_UNRECOGNIZED_COLUMN, prefixStr));
        }
    }

    /**
     * Parses all prefixes in the argument multimap and adds the corresponding filter criteria to
     * the provided map.
     *
     * @param allPrefixes       the list of all prefixes to parse
     * @param argMultimap       the argument multimap containing the parsed arguments
     * @param filterCriteriaMap the map to store the parsed filter criteria
     * @throws ParseException if there is an error parsing any prefix
     */
    private void parsePrefixes(List<Prefix> allPrefixes, ArgumentMultimap argMultimap,
                               Map<ContactColumn, ColumnPredicate> filterCriteriaMap) throws ParseException {
        for (Prefix prefix : allPrefixes) {
            if (argMultimap.getValue(prefix).isEmpty()) {
                continue;
            }
            String inputString = argMultimap.getValue(prefix).get();
            Pair<Operator, String> operatorStringPair = ParserUtil.parseOperatorAndString(inputString);

            // Please forgive me. Basically, to throw exception if tags are duplicate
            if (prefix == PREFIX_CONTACT_TAG_LONG) {
                ParserUtil.parseTags(operatorStringPair.second());
            }

            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, prefix));
            }
            filterCriteriaMap.put(getColumnFromPrefix(prefix), new ColumnPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }
}
