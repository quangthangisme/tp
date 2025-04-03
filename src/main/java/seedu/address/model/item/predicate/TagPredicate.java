package seedu.address.model.item.predicate;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.TaggedItem;
import seedu.address.model.item.commons.Tag;

/**
 * Tests if a {@code TaggedItem} contains the specified tags based on the provided
 * {@code Operator}.
 */
public class TagPredicate implements Predicate<TaggedItem> {
    private final Operator operator;
    private final Set<Tag> keywords;

    /**
     * Constructs a {@code TagPredicate} with the given operator and list of keywords.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the keyword matching logic.
     * @param keywords The list of keywords to search for in the item's tags.
     */
    public TagPredicate(Operator operator, Set<Tag> keywords) {
        this.operator = operator;
        this.keywords = keywords;
    }

    @Override
    public boolean test(TaggedItem item) {
        return operator.apply(keywords.stream(), keyword ->
                item.getTags().stream().anyMatch(tag ->
                        tag.tagName.toLowerCase().contains(keyword.tagName.toLowerCase())));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagPredicate otherPredicate)) {
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
