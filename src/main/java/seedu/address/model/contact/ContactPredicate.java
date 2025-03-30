package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Represents a complex predicate for filtering Contact objects based on multiple criteria.
 * Each criterion consists of a column and associated filter conditions.
 */
public class ContactPredicate implements Predicate<Contact> {

    private final Map<ContactColumn, FilterCriteria> filterCriteriaMap;

    /**
     * Constructs a ContactPredicate with the given filter criteria map.
     *
     * @param filterCriteriaMap map of columns to their filter criteria
     */
    public ContactPredicate(Map<ContactColumn, FilterCriteria> filterCriteriaMap) {
        this.filterCriteriaMap = filterCriteriaMap;
    }

    @Override
    public boolean test(Contact contact) {
        for (Map.Entry<ContactColumn, FilterCriteria> entry : filterCriteriaMap.entrySet()) {
            ContactColumn column = entry.getKey();
            FilterCriteria criteria = entry.getValue();

            List<String> contactValues = getContactValues(contact, column);
            boolean match = criteria.testCriteria(contactValues);

            if (!match) {
                return false;
            }
        }
        return true;
    }

    /**
     * Extracts values from a contact based on the specified column.
     *
     * @param contact the contact to extract values from
     * @param column the column to extract values for
     * @return a list of values from the contact for the specified column
     */
    private List<String> getContactValues(Contact contact, ContactColumn column) {
        List<String> values = new ArrayList<>();
        switch (column) {
        case NAME:
            values.add(contact.getName().fullName.toLowerCase());
            break;
        case EMAIL:
            values.add(contact.getEmail().value.toLowerCase());
            break;
        case ID:
            values.add(contact.getId().fullId.toLowerCase());
            break;
        case COURSE:
            values.add(contact.getCourse().fullModule.toLowerCase());
            break;
        case GROUP:
            values.add(contact.getGroup().fullGroup.toLowerCase());
            break;
        case TAG:
            values.addAll(contact.getTags().stream()
                    .map(tag -> tag.tagName.toLowerCase())
                    .toList());
            break;
        default:
            break;
        }
        return values;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContactPredicate)) {
            return false;
        }

        ContactPredicate otherPredicate = (ContactPredicate) other;
        return filterCriteriaMap.equals(otherPredicate.filterCriteriaMap);
    }
}
