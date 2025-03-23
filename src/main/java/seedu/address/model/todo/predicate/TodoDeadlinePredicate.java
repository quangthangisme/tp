package seedu.address.model.todo.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.todo.Todo;
import seedu.address.model.util.DatetimePredicate;

/**
 * Tests if a {@code Todo}'s deadline satisfies the given {@code DatetimePredicate}s based on the
 * specified {@code Operator}.
 */
public class TodoDeadlinePredicate implements Predicate<Todo> {
    private final Operator operator;
    private final List<DatetimePredicate> predicates;

    /**
     * Constructs an {@code TodoDeadlinePredicate} with the given operator and list of datetime
     * predicates.
     *
     * @param operator   The operator to apply (e.g., AND, OR) to the predicates.
     * @param predicates The list of {@code DatetimePredicate} objects to test against the todo
     *                   deadline.
     */
    public TodoDeadlinePredicate(Operator operator, List<DatetimePredicate> predicates) {
        this.operator = operator;
        this.predicates = predicates;
    }

    @Override
    public boolean test(Todo todo) {
        return operator.apply(predicates.stream(),
                predicate -> predicate.test(todo.getDeadline().deadline));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoDeadlinePredicate otherPredicate)) {
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
