package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_COLUMN;

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

    private static final PrefixAlias NAME_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_NAME;
    private static final PrefixAlias EMAIL_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_EMAIL;
    private static final PrefixAlias ID_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_ID;
    private static final PrefixAlias COURSE_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_COURSE;
    private static final PrefixAlias GROUP_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_GROUP;
    private static final PrefixAlias TAG_PREFIX = ContactCliSyntax.PREFIX_ALIAS_CONTACT_TAG;

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
        Prefix[] listOPrefixes = new PrefixAliasListBuilder()
                .add(NAME_PREFIX, EMAIL_PREFIX, ID_PREFIX, COURSE_PREFIX, GROUP_PREFIX, TAG_PREFIX)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOPrefixes);

        argMultimap.verifyNoDuplicatePrefixesFor(listOPrefixes);

        Map<ContactColumn, ColumnPredicate> filterCriteriaMap = new HashMap<>();

        parsePrefixes(argMultimap, filterCriteriaMap);

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
    private ContactColumn getColumnFromPrefix(PrefixAlias prefix) throws ParseException {
        if (prefix.equals(ID_PREFIX)) {
            return ContactColumn.ID;
        } else if (prefix.equals(NAME_PREFIX)) {
            return ContactColumn.NAME;
        } else if (prefix.equals(EMAIL_PREFIX)) {
            return ContactColumn.EMAIL;
        } else if (prefix.equals(TAG_PREFIX)) {
            return ContactColumn.TAG;
        } else if (prefix.equals(COURSE_PREFIX)) {
            return ContactColumn.COURSE;
        } else if (prefix.equals(GROUP_PREFIX)) {
            return ContactColumn.GROUP;
        } else {
            throw new ParseException(String.format(MESSAGE_UNRECOGNIZED_COLUMN, prefix));
        }
    }

    /**
     * Parses all prefixes in the argument multimap and adds the corresponding filter criteria to
     * the provided map.
     *
     * @param argMultimap       the argument multimap containing the parsed arguments
     * @param filterCriteriaMap the map to store the parsed filter criteria
     * @throws ParseException if there is an error parsing any prefix
     */
    private void parsePrefixes(ArgumentMultimap argMultimap,
                               Map<ContactColumn, ColumnPredicate> filterCriteriaMap) throws ParseException {
        List<PrefixAlias> listOPrefixes = List.of(NAME_PREFIX, EMAIL_PREFIX, ID_PREFIX, COURSE_PREFIX,
                GROUP_PREFIX, TAG_PREFIX);
        for (PrefixAlias prefix : listOPrefixes) {
            if (argMultimap.getValue(prefix).isEmpty()) {
                continue;
            }
            String inputString = argMultimap.getValue(prefix).get();
            Pair<Operator, String> operatorStringPair = ParserUtil.parseOperatorAndString(inputString);

            // Please forgive me. Basically, to throw exception if tags are duplicate
            if (prefix == TAG_PREFIX) {
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
