package seedu.address.model.event;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemInvolvingContactManager;

/**
 * Wraps all {@code Event}-related data. Duplicates are not allowed.
 */
public class EventManager extends ItemInvolvingContactManager<Event> {

    /**
     * Creates an EventManager using the Events in the {@code copy}
     */
    public EventManager(ItemInvolvingContactManager<Event> copy) {
        this();
        resetData(copy);
    }

    public EventManager() {
        super(new UniqueEventList());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("events", getItemList()).toString();
    }
}
