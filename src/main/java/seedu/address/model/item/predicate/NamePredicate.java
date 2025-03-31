package seedu.address.model.item.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.NamedItem;

/**
 * Tests if a {@code NamedItem}'s name contains the specified keywords based on the provided
 * {@code Operator}.
 */
public class NamePredicate implements Predicate<NamedItem> {
    private final Operator operator;
    private final List<String> keywords;

    /**
     * Constructs a {@code NamePredicate} with the given operator and list of keywords.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the keyword matching logic.
     * @param keywords The list of keywords to search for in the item's name.
     */
    public NamePredicate(Operator operator, List<String> keywords) {
        this.operator = operator;
        this.keywords = keywords;
    }

    @Override
    public boolean test(NamedItem item) {
        return operator.apply(keywords.stream(), keyword ->
                item.getName().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NamePredicate otherPredicate)) {
            return false;
        }

        return operator.equals(otherPredicate.operator)
                && keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("operator", operator)
                .add("keywords", keywords).toString();
    }
}
