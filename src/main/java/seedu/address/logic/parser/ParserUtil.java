package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_DUPLICATE_INDEX = "Duplicate index found: %1$s";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
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
        if (trimmedIndices.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        List<Index> indexList = new ArrayList<>();
        Set<Index> seenIndices = new HashSet<>();

        for (String strIndex : trimmedIndices.split("\\s+")) {
            if (!StringUtil.isNonZeroUnsignedInteger(strIndex)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
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
}
