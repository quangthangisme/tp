package seedu.address.ui.util;

import seedu.address.model.event.Event;
import seedu.address.ui.card.CardFactory;

/**
 * Adapter for Event objects to implement DisplayableItem interface.
 */
public class EventAdapter extends ItemAdapter<Event> {
    /**
     * Constructs an EventAdapter with the specified event and card factory.
     *
     * @param event The Event object to adapt
     * @param cardFactory The factory used to create display cards for this event
     */
    public EventAdapter(Event event, CardFactory<Event> cardFactory) {
        super(event, cardFactory);
    }

    /**
     * Gets the event being adapted.
     *
     * @return The event
     */
    public Event getEvent() {
        return getEntity();
    }
}
