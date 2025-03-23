package seedu.address.model.event.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;

/**
 * Tests if an {@code Event}'s location contains the specified keywords based on the provided
 * {@code Operator}.
 */
public class EventLocationPredicate implements Predicate<Event> {
    private final Operator operator;
    private final List<String> keywords;

    /**
     * Constructs an {@code EventLocationPredicate} with the given operator and list of keywords.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the keyword matching logic.
     * @param keywords The list of keywords to search for in the event's location.
     */
    public EventLocationPredicate(Operator operator, List<String> keywords) {
        this.operator = operator;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return operator.apply(keywords.stream(), keyword
                -> StringUtil.containsWordIgnoreCase(event.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventLocationPredicate otherPredicate)) {
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
