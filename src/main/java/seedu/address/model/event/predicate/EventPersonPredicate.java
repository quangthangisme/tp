package seedu.address.model.event.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

public class EventPersonPredicate implements Predicate<Event> {
    private final Operator operator;
    private final List<Person> persons;

    public EventPersonPredicate(Operator operator, List<Person> persons) {
        this.operator = operator;
        this.persons = persons;
    }

    @Override
    public boolean test(Event event) {
        return operator.apply(persons.stream(), person -> event.getPersons().contains(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventPersonPredicate otherPredicate)) {
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
