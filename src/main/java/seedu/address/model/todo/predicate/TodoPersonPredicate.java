package seedu.address.model.todo.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;

public class TodoContactPredicate implements Predicate<Todo> {
    private final Operator operator;
    private final List<Person> persons;

    public TodoContactPredicate(Operator operator, List<Person> persons) {
        this.operator = operator;
        this.persons = persons;
    }

    @Override
    public boolean test(Todo todo) {
        return operator.apply(persons.stream(), person -> todo.getPersons().contains(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TodoContactPredicate otherPredicate)) {
            return false;
        }

        return operator.equals(otherPredicate.operator)
                && persons.equals(otherPredicate.persons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("operator", operator)
                .add("persons", persons).toString();
    }
}
