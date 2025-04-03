package seedu.address.ui.util;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.todo.Todo;
import seedu.address.ui.card.ContactCardFactory;
import seedu.address.ui.card.EventCardFactory;
import seedu.address.ui.card.TodoCardFactory;

/**
 * Utility class to convert between different list types.
 */
public class ListConverter {

    /**
     * Converts a list of Contact objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableContactList(ObservableList<Contact> contactList) {
        ContactCardFactory factory = new ContactCardFactory();
        return FXCollections.observableList(
                contactList.stream()
                        .map(contact -> new ContactAdapter(contact, factory))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a list of Event objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableEventList(ObservableList<Event> eventList) {
        EventCardFactory factory = new EventCardFactory();
        return FXCollections.observableList(
                eventList.stream()
                        .map(event -> new EventAdapter(event, factory))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a list of Todo objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableTodoList(ObservableList<Todo> todoList) {
        TodoCardFactory factory = new TodoCardFactory();
        return FXCollections.observableList(
                todoList.stream()
                        .map(todo -> new TodoAdapter(todo, factory))
                        .collect(Collectors.toList())
        );
    }
}
