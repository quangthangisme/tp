package seedu.address.model.contact;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.model.ColumnPredicate;

/**
 * Represents a complex predicate for filtering Contact objects based on multiple criteria.
 * Each criterion consists of a column and associated filter conditions.
 */
public class ContactPredicate implements Predicate<Contact> {

    private final Map<ContactColumn, ColumnPredicate> filterCriteriaMap;

    /**
     * Constructs a ContactPredicate with the given filter criteria map.
     *
     * @param filterCriteriaMap map of columns to their filter criteria
     */
    public ContactPredicate(Map<ContactColumn, ColumnPredicate> filterCriteriaMap) {
        this.filterCriteriaMap = filterCriteriaMap;
    }

    @Override
    public boolean test(Contact contact) {
        return filterCriteriaMap.entrySet().stream()
                .allMatch(entry ->
                        entry.getValue().test(getContactValues(contact, entry.getKey())));
    }

    /**
     * Extracts values from a contact based on the specified column.
     *
     * @param contact the contact to extract values from
     * @param column  the column to extract values for
     * @return a list of values from the contact for the specified column
     */
    private List<String> getContactValues(Contact contact, ContactColumn column) {
        switch (column) {
        case NAME:
            return List.of(contact.getName().value);
        case EMAIL:
            return List.of(contact.getEmail().value);
        case ID:
            return List.of(contact.getId().fullId);
        case COURSE:
            return List.of(contact.getCourse().fullCourse);
        case GROUP:
            return List.of(contact.getGroup().fullGroup);
        case TAG:
            return contact.getTags().stream().map(tag -> tag.tagName).toList();
        // Should never happen: guard clause in case of adding new columns
        default:
            throw new UnsupportedOperationException("Column not supported: " + column);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContactPredicate otherPredicate)) {
            return false;
        }

        return filterCriteriaMap.equals(otherPredicate.filterCriteriaMap);
    }

    @Override
    public int hashCode() {
        return filterCriteriaMap.hashCode();
    }
}
