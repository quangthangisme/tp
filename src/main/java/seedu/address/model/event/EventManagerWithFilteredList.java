package seedu.address.model.event;

import java.util.Comparator;

import seedu.address.model.item.ItemInvolvingContactManager;
import seedu.address.model.item.ItemInvolvingContactManagerWithFilteredList;

/**
 * Manages a list of {@code Event} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class EventManagerWithFilteredList extends ItemInvolvingContactManagerWithFilteredList<Event>
        implements EventManagerAndList {

    public EventManagerWithFilteredList(ItemInvolvingContactManager<Event> eventManager) {
        super(eventManager);
    }

    public EventManagerWithFilteredList() {
        super(new EventManager());
    }

    @Override
    public Comparator<Event> getDefaultComparator() {
        return Comparator.comparing(Event::getStartTime).thenComparing(Event::getEndTime)
                .thenComparing(Event::getName);
    }
}
