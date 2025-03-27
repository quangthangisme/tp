package seedu.address.model.todo.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;

/**
 * Tests if a {@code Todo}'s linked persons contains the specified persons based on the provided
 * {@code Operator}.
 */
public class TodoPersonPredicate implements Predicate<Todo> {
    private final Operator operator;
    private final List<Person> persons;

    /**
     * Constructs a {@code TodoNamePredicate} with the given operator and list of persons.
     *
     * @param operator The operator to apply (e.g., AND, OR) to the persons matching logic.
     * @param persons  The list of persons to search for in the todo's linked persons.
     */
    public TodoPersonPredicate(Operator operator, List<Person> persons) {
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
        if (!(other instanceof TodoPersonPredicate otherPredicate)) {
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
