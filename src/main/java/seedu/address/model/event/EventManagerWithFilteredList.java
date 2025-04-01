package seedu.address.model.event;

import java.util.Comparator;

import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Manages a list of {@code Event} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class EventManagerWithFilteredList extends ItemManagerWithFilteredList<Event> {
    public EventManagerWithFilteredList(ItemManager<Event> eventManager) {
        super(eventManager);
    }

    public EventManagerWithFilteredList() {
        super(new EventManager());
    }

    @Override
    protected Comparator<Event> getDefaultComparator() {
        return (event1, event2) -> {
            int startComparison = event1.getStartTime().compareTo(event2.getStartTime());
            if (startComparison != 0) {
                return startComparison;
            }

            int endComparison = event1.getEndTime().compareTo(event2.getEndTime());
            if (endComparison != 0) {
                return endComparison;
            }

            return event1.getName().compareTo(event2.getName());
        };
    }
}
