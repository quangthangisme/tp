package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.todo.Todo;

import java.util.stream.Collectors;

/**
 * Utility class to convert between different list types.
 */
public class ListConverter {

    /**
     * Converts a list of Contact objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableContactList(ObservableList<Contact> contactList) {
        return FXCollections.observableList(
                contactList.stream()
                        .map(contact -> new ContactAdapter(contact, new ContactCardFactory()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a list of Event objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableEventList(ObservableList<Event> eventList) {
        return FXCollections.observableList(
                eventList.stream()
                        .map(event -> new EventAdapter(event, new EventCardFactory()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a list of Todo objects to a list of DisplayableItem objects.
     */
    public static ObservableList<DisplayableItem> toDisplayableTodoList(ObservableList<Todo> todoList) {
        return FXCollections.observableList(
                todoList.stream()
                        .map(todo -> new TodoAdapter(todo, new TodoCardFactory()))
                        .collect(Collectors.toList())
        );
    }
}
