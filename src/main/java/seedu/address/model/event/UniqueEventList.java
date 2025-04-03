package seedu.address.model.event;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.item.UniqueItemInvolvingContactList;

/**
 * A list of Events that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class UniqueEventList extends UniqueItemInvolvingContactList<Event> {
    public UniqueEventList() {
        super(new EventDuplicateChecker());
    }

    @Override
    public void throwDuplicateException() {
        throw new DuplicateEventException();
    }

    @Override
    public void throwNotFoundException() {
        throw new EventNotFoundException();
    }
}
