package seedu.address.logic.parser;

import static seedu.address.model.item.predicate.DatetimePredicate.DATE_PATTERN;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.predicate.DatetimePredicate;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX =
            "Index is not a non-zero unsigned integer: %1$s";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate index found: %1$s";
    public static final String MESSAGE_INVALID_BOOLEAN = "Invalid boolean value: %1$s";
    public static final String MESSAGE_INVALID_DATETIME_PAIR = "Invalid datetime pair: %1$s";
    public static final String MESSAGE_INVALID_DATETIME_PREDICATE =
            "Invalid format in datetime predicates.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(String.format(MESSAGE_INVALID_INDEX, trimmedIndex));
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndices} into a {@code List<Index>} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if any specified index is invalid (not a non-zero unsigned integer) or
     *                        if there are duplicate indices.
     */
    public static List<Index> parseIndices(String oneBasedIndices) throws ParseException {
        String trimmedIndices = oneBasedIndices.trim();
        String[] indices = trimmedIndices.isEmpty() ? new String[0] : trimmedIndices.split("\\s+");

        List<Index> indexList = new ArrayList<>();
        Set<Index> seenIndices = new HashSet<>();

        for (String strIndex : indices) {
            if (!StringUtil.isNonZeroUnsignedInteger(strIndex)) {
                throw new ParseException(String.format(MESSAGE_INVALID_INDEX, strIndex));
            }

            Index index = Index.fromOneBased(Integer.parseInt(strIndex));
            if (seenIndices.contains(index)) {
                throw new ParseException(String.format(MESSAGE_DUPLICATE_INDEX,
                        index.getOneBased()));
            }

            seenIndices.add(index);
            indexList.add(index);
        }

        return indexList.stream().toList();
    }

    /**
     * Parses the given string to extract the operator and the remaining string. If the string
     * starts with a recognized operator (e.g., "and", "or"), it returns the operator and the
     * remaining string. Otherwise, it defaults to the AND operator.
     */
    public static Pair<Operator, String> parseOperatorAndString(String input) {
        input = input.trim();
        for (Operator operator : Operator.values()) {
            String operatorPrefix = operator.getName() + ":";
            if (input.toLowerCase().startsWith(operatorPrefix)) {
                return new Pair<>(operator, input.substring(operatorPrefix.length()).trim());
            }
        }
        return new Pair<>(Operator.AND, input);
    }

    /**
     * Parses {@code booleanString} into a {@code boolean} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified string is neither "true" nor "false"
     *                        (case-insensitive).
     */
    public static boolean parseBoolean(String booleanString) throws ParseException {
        String trimmedString = booleanString.trim().toLowerCase();

        if ("true".equals(trimmedString)) {
            return true;
        } else if ("false".equals(trimmedString)) {
            return false;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_BOOLEAN, booleanString));
        }
    }

    /**
     * Parses {@code predicatesString} into a {@code List<DatetimePredicate>} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static List<DatetimePredicate> parseDatetimePredicates(String predicatesString)
            throws ParseException {
        List<DatetimePredicate> predicates = new ArrayList<>();

        String remainingString = predicatesString.trim();
        Matcher matcher = Pattern.compile(DATE_PATTERN).matcher(remainingString);

        while (!remainingString.isEmpty()) {
            if (matcher.lookingAt()) {
                String predicateStr = matcher.group();
                System.out.println(predicateStr);
                if (DatetimePredicate.isValid(predicateStr)) {
                    predicates.add(new DatetimePredicate(predicateStr));
                    remainingString = remainingString.substring(predicateStr.length()).trim();
                    matcher = Pattern.compile(DATE_PATTERN).matcher(remainingString);
                } else {
                    throw new ParseException(String.format(MESSAGE_INVALID_DATETIME_PAIR, predicateStr));
                }
            } else {
                throw new ParseException(MESSAGE_INVALID_DATETIME_PREDICATE);
            }
        }

        return List.copyOf(predicates);
    }

}
