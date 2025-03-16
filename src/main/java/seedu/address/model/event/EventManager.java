package seedu.address.model.event;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemManager;

/**
 * Wraps all {@code Event}-related data. Duplicates are not allowed.
 */
public class EventManager extends ItemManager<Event> {
    public EventManager(ItemManager<Event> copy) {
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
