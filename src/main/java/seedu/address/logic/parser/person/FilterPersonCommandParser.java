package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_COLUMN;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_COLUMN;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_OPERATOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterCriteria;
import seedu.address.model.person.PersonPredicate;
import seedu.address.model.person.Column;
import seedu.address.model.person.Operator;

/**
 * Parses input arguments and creates a new FilterPersonCommand object.
 */
public class FilterPersonCommandParser implements Parser<FilterPersonCommand> {

    private static final Pattern OPERATOR_FORMAT = Pattern.compile("^([a-zA-Z]+):");

    private static final Pattern QUOTED_VALUE_PATTERN = Pattern.compile("\"([^\"]*)\"");

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

    private void parsePrefixes(List<Prefix> allPrefixes, ArgumentMultimap argMultimap, Map<Column, FilterCriteria> filterCriteriaMap) throws ParseException {
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

    private void parseValues(Prefix prefix, Column column, Map<Column, FilterCriteria> filterCriteriaMap, List<String> rawValues) throws ParseException {
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
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
        }

        else if (values.isEmpty() && !input.trim().isEmpty()) {
            for (String value : input.trim().split("\\s+")) {
                if (!value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }

        return values;
    }
}