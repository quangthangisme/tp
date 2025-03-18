package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Represents a complex predicate for filtering Person objects based on multiple criteria.
 * Each criterion consists of a column and associated filter conditions.
 */
public class PersonPredicate implements Predicate<Person> {

    private final Map<Column, FilterCriteria> filterCriteriaMap;

    /**
     * Constructs a PersonPredicate with the given filter criteria map.
     *
     * @param filterCriteriaMap map of columns to their filter criteria
     */
    public PersonPredicate(Map<Column, FilterCriteria> filterCriteriaMap) {
        this.filterCriteriaMap = filterCriteriaMap;
    }

    @Override
    public boolean test(Person person) {
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

    /**
     * Extracts values from a person based on the specified column.
     *
     * @param person the person to extract values from
     * @param column the column to extract values for
     * @return a list of values from the person for the specified column
     */
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
        default:
            break;
        }
        return values;
    }

    /**
     * Tests if the person's values match the filter criteria.
     *
     * @param personValues the values from the person
     * @param criteria the filter criteria to match against
     * @return true if the values match the criteria, false otherwise
     */
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
