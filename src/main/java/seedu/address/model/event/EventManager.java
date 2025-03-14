package seedu.address.model.event;

import seedu.address.model.item.ItemManager;

/**
 * Wraps all {@code Event}-related data. Duplicates are not allowed.
 */
public class EventManager extends ItemManager<Event> {
    public EventManager() {
        super(new UniqueEventList());
    }
}
