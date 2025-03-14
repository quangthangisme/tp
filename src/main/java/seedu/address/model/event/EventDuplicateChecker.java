package seedu.address.model.event;

import seedu.address.model.item.DuplicateChecker;

/**
 * Checks if two given events are considered duplicates of each other.
 */
public class EventDuplicateChecker implements DuplicateChecker<Event> {
    @Override
    public boolean check(Event first, Event second) {
        return first == second || (first != null && first.getName().equals(second.getName()));
    }
}
