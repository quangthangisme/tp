package seedu.address.model.event.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.util.DatetimePredicate;

public class EventStartTimePredicate implements Predicate<Event> {
    private final Operator operator;
    private final List<DatetimePredicate> predicates;

    public EventStartTimePredicate(Operator operator, List<DatetimePredicate> predicates) {
        this.operator = operator;
        this.predicates = predicates;
    }

    @Override
    public boolean test(Event event) {
        return operator.apply(predicates.stream(),
                predicate -> predicate.test(event.getStartTime().dateTime));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventStartTimePredicate otherPredicate)) {
            return false;
        }

        return operator.equals(otherPredicate.operator)
                && predicates.equals(otherPredicate.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("operator", operator)
                .add("predicates", predicates).toString();
    }
}
