package seedu.address.model.event.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.todo.Todo;

public class TodoNamePredicate implements Predicate<Todo> {
    private final Operator operator;
    private final List<String> keywords;

    public TodoNamePredicate(Operator operator, List<String> keywords) {
        this.operator = operator;
        this.keywords = keywords;
    }

    @Override
    public boolean test(Todo todo) {
        return operator.apply(keywords.stream(), keyword
                -> StringUtil.containsWordIgnoreCase(todo.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoNamePredicate otherPredicate)) {
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
