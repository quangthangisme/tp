package seedu.address.ui.util;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.todo.Todo;
import seedu.address.ui.card.ContactCard;
import seedu.address.ui.card.EventCard;
import seedu.address.ui.card.GenericCardFactory;
import seedu.address.ui.card.TodoCard;

/**
 * Utility class to convert between different list types.
 * Uses the generic adapter pattern to convert domain objects to displayable items.
 */
public class ListConverter {
    private static final GenericCardFactory<Contact> CONTACT_CARD_FACTORY =
            new GenericCardFactory<>(ContactCard::new);

    private static final GenericCardFactory<Event> EVENT_CARD_FACTORY =
            new GenericCardFactory<>(EventCard::new);

    private static final GenericCardFactory<Todo> TODO_CARD_FACTORY =
            new GenericCardFactory<>(TodoCard::new);

    /**
     * Converts a list of Contact objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableContactList(ObservableList<Contact> contactList) {
        return FXCollections.observableList(
                contactList.stream()
                        .map(contact -> new GenericAdapter<>(contact, CONTACT_CARD_FACTORY))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a list of Event objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableEventList(ObservableList<Event> eventList) {
        return FXCollections.observableList(
                eventList.stream()
                        .map(event -> new GenericAdapter<>(event, EVENT_CARD_FACTORY))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a list of Todo objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableTodoList(ObservableList<Todo> todoList) {
        return FXCollections.observableList(
                todoList.stream()
                        .map(todo -> new GenericAdapter<>(todo, TODO_CARD_FACTORY))
                        .collect(Collectors.toList())
        );
    }
}
