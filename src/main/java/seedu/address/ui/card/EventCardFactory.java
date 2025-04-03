package seedu.address.ui.card;

import seedu.address.model.event.Event;
import seedu.address.ui.UiPart;

/**
 * Factory for creating display cards for Event objects.
 * Implements the CardFactory interface for Event type.
 */
public class EventCardFactory implements CardFactory<Event> {
    @Override
    public UiPart<?> createCard(Event event, int index) {
        return new EventCard(event, index);
    }
}
