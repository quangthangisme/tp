package seedu.address.ui.card;

import seedu.address.model.event.Event;
import seedu.address.ui.UiPart;

public class EventCardFactory implements CardFactory<Event> {
    @Override
    public UiPart<?> createCard(Event event, int index) {
        return new EventCard(event, index);
    }
}
