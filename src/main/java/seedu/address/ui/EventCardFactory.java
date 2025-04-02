package seedu.address.ui;

import seedu.address.model.event.Event;

public class EventCardFactory implements CardFactory<Event> {
    @Override
    public UiPart<?> createCard(Event event, int index) {
        return new EventCard(event, index);
    }
}
