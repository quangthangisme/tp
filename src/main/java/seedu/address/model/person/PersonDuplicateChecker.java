package seedu.address.model.person;

import seedu.address.model.item.DuplicateChecker;

/**
 * Checks if two given persons are considered duplicates of each other.
 */
public class PersonDuplicateChecker implements DuplicateChecker<Person> {
    @Override
    public boolean check(Person first, Person second) {
        return first == second || (first != null && first.getId().equals(second.getId()));
    }
}
