package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Represents a complex predicate for filtering Person objects.
 */
public class PersonPredicate implements Predicate<Person> {

    private final Map<Column, FilterCriteria> filterCriteriaMap;

    /**
     * Constructs a PersonPredicate with the given filter criteria map.
     */
    public PersonPredicate(Map<Column, FilterCriteria> filterCriteriaMap) {
        this.filterCriteriaMap = filterCriteriaMap;
    }

    @Override
    public boolean test(Person person) {
        // Test each filter criterion
        for (Map.Entry<Column, FilterCriteria> entry : filterCriteriaMap.entrySet()) {
            Column column = entry.getKey();
            FilterCriteria criteria = entry.getValue();

            List<String> personValues = getPersonValues(person, column);
            boolean match = testCriteria(personValues, criteria);

            if (!match) {
                return false;
            }
        }
        return true;
    }

    private List<String> getPersonValues(Person person, Column column) {
        List<String> values = new ArrayList<>();
        switch (column) {
        case NAME:
            values.add(person.getName().fullName.toLowerCase());
            break;
        case PHONE:
            values.add(person.getPhone().value.toLowerCase());
            break;
        case EMAIL:
            values.add(person.getEmail().value.toLowerCase());
            break;
        case ID:
            values.add(person.getId().fullId.toLowerCase());
            break;
        case COURSE:
            values.add(person.getCourse().fullModule.toLowerCase());
            break;
        case GROUP:
            values.add(person.getGroup().fullGroup.toLowerCase());
            break;
        case TAG:
            values.addAll(person.getTags().stream()
                    .map(tag -> tag.tagName.toLowerCase())
                    .toList());
            break;
        }
        return values;
    }

    private boolean testCriteria(List<String> personValues, FilterCriteria criteria) {
        return switch (criteria.getOperator()) {
            case AND -> criteria.getValues().stream().allMatch(value ->
                    personValues.stream().anyMatch(pv -> pv.contains(value.toLowerCase())));
            case OR -> criteria.getValues().stream().anyMatch(value ->
                    personValues.stream().anyMatch(pv -> pv.contains(value.toLowerCase())));
            case NAND -> !criteria.getValues().stream().allMatch(value ->
                    personValues.stream().anyMatch(pv -> pv.contains(value.toLowerCase())));
            case NOR -> criteria.getValues().stream().noneMatch(value ->
                    personValues.stream().anyMatch(pv -> pv.contains(value.toLowerCase())));
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonPredicate)) {
            return false;
        }

        PersonPredicate otherPredicate = (PersonPredicate) other;
        return filterCriteriaMap.equals(otherPredicate.filterCriteriaMap);
    }
}