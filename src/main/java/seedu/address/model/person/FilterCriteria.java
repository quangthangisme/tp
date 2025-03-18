package seedu.address.model.person;

import java.util.List;

/**
 * Represents the filter criteria for a single column.
 */
public class FilterCriteria {
    private final Operator operator;
    private final List<String> values;

    public FilterCriteria(Operator operator, List<String> values) {
        this.operator = operator;
        this.values = values;
    }

    public Operator getOperator() {
        return operator;
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCriteria)) {
            return false;
        }

        FilterCriteria otherCriteria = (FilterCriteria) other;
        return operator == otherCriteria.operator
                && values.equals(otherCriteria.values);
    }
}