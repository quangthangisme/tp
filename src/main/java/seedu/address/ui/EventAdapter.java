package seedu.address.ui;

import seedu.address.model.event.Event;

/**
 * Adapter for Event objects to implement DisplayableItem interface.
 */
public class EventAdapter implements DisplayableItem {
    private final Event event;
    private final CardFactory<Event> cardFactory;

    public EventAdapter(Event event, CardFactory<Event> cardFactory) {
        this.event = event;
        this.cardFactory = cardFactory;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public UiPart<?> getDisplayCard(int index) {
        return cardFactory.createCard(event, index);
    }
}
