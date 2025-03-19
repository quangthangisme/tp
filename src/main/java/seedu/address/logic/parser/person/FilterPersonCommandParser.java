package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_COLUMN;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_COLUMN;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_OPERATOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Column;
import seedu.address.model.person.FilterCriteria;
import seedu.address.model.person.Operator;
import seedu.address.model.person.PersonPredicate;

/**
 * Parses input arguments and creates a new FilterPersonCommand object.
 * Handles complex filter criteria with different operators and values.
 */
public class FilterPersonCommandParser implements Parser<FilterPersonCommand> {

    private static final Pattern OPERATOR_FORMAT = Pattern.compile("^([a-zA-Z]+):");

    private static final Pattern QUOTED_VALUE_PATTERN = Pattern.compile("\"([^\"]*)\"");

    /**
     * Converts a prefix to its corresponding column.
     *
     * @param prefix the prefix to convert
     * @return the corresponding column
     * @throws ParseException if the prefix does not correspond to a valid column
     */
    private Column getColumnFromPrefix(Prefix prefix) throws ParseException {
        String prefixStr = prefix.getPrefix();

        if (prefixStr.equals(PREFIX_NAME.getPrefix())) {
            return Column.NAME;
        } else if (prefixStr.equals(PREFIX_PHONE.getPrefix())) {
            return Column.PHONE;
        } else if (prefixStr.equals(PREFIX_EMAIL.getPrefix())) {
            return Column.EMAIL;
        } else if (prefixStr.equals(PREFIX_TAG.getPrefix())) {
            return Column.TAG;
        } else if (prefixStr.equals(PREFIX_COURSE.getPrefix())) {
            return Column.COURSE;
        } else if (prefixStr.equals(PREFIX_GROUP.getPrefix())) {
            return Column.GROUP;
        } else {
            throw new ParseException(
                    String.format(MESSAGE_UNRECOGNIZED_COLUMN, prefixStr)
            );
        }
    }

    /**
     * Parses all prefixes in the argument multimap and adds the corresponding filter criteria
     * to the provided map.
     *
     * @param allPrefixes       the list of all prefixes to parse
     * @param argMultimap       the argument multimap containing the parsed arguments
     * @param filterCriteriaMap the map to store the parsed filter criteria
     * @throws ParseException if there is an error parsing any prefix
     */
    private void parsePrefixes(List<Prefix> allPrefixes, ArgumentMultimap argMultimap,
                               Map<Column, FilterCriteria> filterCriteriaMap) throws ParseException {
        for (Prefix prefix : allPrefixes) {
            List<String> rawValues = argMultimap.getAllValues(prefix);
            if (rawValues.isEmpty()) {
                continue;
            }

            try {
                Column column = getColumnFromPrefix(prefix);

                parseValues(prefix, column, filterCriteriaMap, rawValues);
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        String.format(MESSAGE_UNRECOGNIZED_COLUMN, prefix.getPrefix())
                );
            }
        }
    }

    /**
     * Parses the values for a specific prefix and adds the corresponding filter criteria
     * to the provided map.
     *
     * @param prefix            the prefix being parsed
     * @param column            the column corresponding to the prefix
     * @param filterCriteriaMap the map to store the parsed filter criteria
     * @param rawValues         the list of raw values to parse
     * @throws ParseException if there is an error parsing the values
     */
    private void parseValues(Prefix prefix, Column column, Map<Column, FilterCriteria> filterCriteriaMap,
                             List<String> rawValues) throws ParseException {
        if (filterCriteriaMap.containsKey(column)) {
            throw new ParseException(MESSAGE_DUPLICATE_COLUMN);
        }

        String firstValue = rawValues.get(0);
        Operator operator = Operator.AND; // Default operator
        List<String> values = new ArrayList<>();

        Matcher operatorMatcher = OPERATOR_FORMAT.matcher(firstValue);
        if (operatorMatcher.find()) {
            String operatorStr = operatorMatcher.group(1);
            try {
                operator = Operator.valueOf(operatorStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        String.format(MESSAGE_UNRECOGNIZED_OPERATOR, operatorStr)
                );
            }

            firstValue = firstValue.substring(operatorMatcher.end()).trim();
            if (!firstValue.isEmpty()) {
                values.addAll(extractValues(firstValue));
            }
        } else {
            values.addAll(extractValues(firstValue));
        }

        for (int i = 1; i < rawValues.size(); i++) {
            values.addAll(extractValues(rawValues.get(i)));
        }

        if (values.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_NO_VALUES, prefix.getPrefix())
            );
        }

        filterCriteriaMap.put(column, new FilterCriteria(operator, values));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPersonCommand
     * and returns a FilterPersonCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterPersonCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPersonCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ID, PREFIX_COURSE, PREFIX_GROUP, PREFIX_TAG);

        Map<Column, FilterCriteria> filterCriteriaMap = new HashMap<>();

        List<Prefix> allPrefixes = List.of(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ID, PREFIX_COURSE, PREFIX_GROUP, PREFIX_TAG);

        parsePrefixes(allPrefixes, argMultimap, filterCriteriaMap);

        if (filterCriteriaMap.isEmpty()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterPersonCommand(new PersonPredicate(filterCriteriaMap));
    }

    /**
     * Extracts individual values from an input string, handling both quoted and unquoted values.
     *
     * @param input the input string to extract values from
     * @return a list of extracted values
     */
    private List<String> extractValues(String input) {
        List<String> values = new ArrayList<>();
        Matcher quotedValueMatcher = QUOTED_VALUE_PATTERN.matcher(input);

        int lastPosition = 0;

        while (quotedValueMatcher.find()) {
            values.add(quotedValueMatcher.group(1).trim());
            lastPosition = quotedValueMatcher.end();
        }

        if (lastPosition > 0 && lastPosition < input.length()) {
            String remaining = input.substring(lastPosition).trim();
            if (!remaining.isEmpty()) {
                for (String value : remaining.split("\\s+")) {
                    if (!value.trim().isEmpty()) {
                        values.add(value.trim());
                    }
                }
            }
        } else if (values.isEmpty() && !input.trim().isEmpty()) {
            for (String value : input.trim().split("\\s+")) {
                if (!value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }

        return values;
    }
}
