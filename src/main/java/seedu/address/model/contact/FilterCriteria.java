package seedu.address.model.contact;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;

/**
 * Represents the filter criteria for a single column.
 * Each criteria consist of a logical operator and a list of values to match against.
 */
public class FilterCriteria {
    private final Operator operator;
    private final List<String> valuesToSearch;

    /**
     * Constructs a FilterCriteria with the given operator and values.
     *
     * @param operator       the logical operator to apply
     * @param valuesToSearch the list of values to match against
     */
    public FilterCriteria(Operator operator, List<String> valuesToSearch) {
        this.operator = operator;
        this.valuesToSearch = valuesToSearch;
    }

    /**
     * Tests if the contact's values match the filter criteria.
     * <p>
     * Concrete example: if the criteria is for name with AND operator and values = ["A", "B"],
     * and the contact's name is "Alice Bob", then the method will return true since both
     * "A" and "B" are in the contact's name.
     *
     * @param itemColumnValues the values from the contact
     * @return true if the values match the criteria, false otherwise
     */
    boolean testCriteria(List<String> itemColumnValues) {
        // Checks if the value is contained within itemColumnValues in a case-insensitive manner
        Predicate<String> isValueContained = searchValue -> itemColumnValues.stream().anyMatch(
                itemValue -> itemValue.toLowerCase().contains(searchValue.toLowerCase())
        );
        return operator.apply(valuesToSearch.stream(), isValueContained);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCriteria otherCriteria)) {
            return false;
        }

        return operator == otherCriteria.operator
                && valuesToSearch.equals(otherCriteria.valuesToSearch);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + operator.hashCode();
        result = 31 * result + valuesToSearch.hashCode();
        return result;
    }
}
