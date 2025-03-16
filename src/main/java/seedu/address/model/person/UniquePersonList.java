package seedu.address.model.person;

import seedu.address.model.item.UniqueItemList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList extends UniqueItemList<Person> {

    /**
     * Constructs a {@code UniqueItemList}.
     */
    public UniquePersonList() {
        super(new PersonDuplicateChecker());
    }

    @Override
    public void throwDuplicateException() {
        throw new DuplicatePersonException();
    }

    @Override
    public void throwNotFoundException() {
        throw new PersonNotFoundException();
    }
}
