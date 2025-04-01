package seedu.address.model.item.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemWithLocation;

/**
 * Tests if a {@code ItemWithLocation}'s location contains the specified keywords based on the
 * provided {@code Operator}.
 */
public class LocationPredicate implements Predicate<ItemWithLocation> {
    private final Operator operator;
    private final List<String> keywords;

    /**
     * Constructs a {@code LocationPredicate} with the given operator and list of keywords.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the keyword matching logic.
     * @param keywords The list of keywords to search for in the item's location.
     */
    public LocationPredicate(Operator operator, List<String> keywords) {
        this.operator = operator;
        this.keywords = keywords;
    }

    @Override
    public boolean test(ItemWithLocation item) {
        return operator.apply(keywords.stream(), keyword ->
                item.getLocation().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LocationPredicate otherPredicate)) {
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
