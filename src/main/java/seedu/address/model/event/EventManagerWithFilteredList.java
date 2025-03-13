package seedu.address.model.event;

import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Manages a list of {@code Event} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class EventManagerWithFilteredList extends ItemManagerWithFilteredList<Event> {
    public EventManagerWithFilteredList() {
        super(new EventManager());
    }
}
