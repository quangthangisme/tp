package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.item.ItemManager;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Id("A0123456X"), new Name("Alex Yeoh"),
                new Email("alexyeoh@example.com"),
                new Course("CS1010S"), new Group("T01"),
                getTagSet("friends")),
            new Contact(new Id("A1234567Y"), new Name("Bernice Yu"),
                new Email("berniceyu@example.com"),
                new Course("CS1010S"), new Group("R01"),
                getTagSet("colleagues", "friends")),
            new Contact(new Id("A2345678Z"), new Name("Charlotte Oliveiro"),
                new Email("charlotte@example.com"),
                new Course("CS2103"), new Group("F08"),
                getTagSet("neighbours")),
            new Contact(new Id("A3456789G"), new Name("David Li"),
                new Email("lidavid@example.com"),
                new Course("CS2030"), new Group("F10"),
                getTagSet("family")),
            new Contact(new Id("A4567890H"), new Name("Irfan Ibrahim"),
                new Email("irfan@example.com"),
                new Course("CS2040"), new Group("T10"),
                getTagSet("classmates")),
            new Contact(new Id("A5678901K"), new Name("Roy Balakrishnan"),
                new Email("royb@example.com"),
                new Course("CS1010S"), new Group("T28"),
                getTagSet("colleagues"))
        };
    }

    public static Todo[] getSampleTodos() {
        return new Todo[] {
            new Todo(new Name("Grading"), new Datetime("25-03-23 17:00"),
                new Location("NUS Science")),
            new Todo(new Name("Lab 1"), new Datetime("27-03-23 17:00"),
                new Location("NUS Com1")),
            new Todo(new Name("Project Meeting"), new Datetime("28-03-23 19:00"),
                new Location("NUS Engineering")),
            new Todo(new Name("CS2103 Lecture"), new Datetime("29-03-23 14:00"),
                new Location("NUS Business")),
            new Todo(new Name("CS2030 Tutorial"), new Datetime("30-03-23 10:00"),
                new Location("NUS SoC")),
            new Todo(new Name("CS2040 Lab"), new Datetime("31-03-23 17:00"),
                new Location("NUS Com2")),
            new Todo(new Name("CS1010S Project"), new Datetime("01-04-23 23:59"),
                new Location("NUS Computing"))
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Name("Hackathon"), new Datetime("25-03-23 09:00"),
                new Datetime("25-03-23 17:00"), new Location("NUS UTown")),
            new Event(new Name("Workshop"), new Datetime("27-03-23 13:00"),
                new Datetime("27-03-23 17:00"), new Location("NUS Com2")),
            new Event(new Name("Seminar"), new Datetime("28-03-23 15:00"),
                new Datetime("28-03-23 17:00"), new Location("NUS LT27")),
            new Event(new Name("Meeting"), new Datetime("29-03-23 11:00"),
                new Datetime("29-03-23 13:00"), new Location("NUS AS6")),
            new Event(new Name("Conference"), new Datetime("30-03-23 09:00"),
                new Datetime("30-03-23 17:00"), new Location("NUS LT19"))
        };
    }

    public static ItemManager<Contact> getSampleAddressBook() {
        ContactManager sampleAb = new ContactManager();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addItem(sampleContact);
        }
        return sampleAb;
    }

    public static ItemManager<Todo> getSampleTodoList() {
        TodoManager sampleTd = new TodoManager();
        for (Todo sampleTodo : getSampleTodos()) {
            sampleTd.addItem(sampleTodo);
        }
        return sampleTd;
    }

    public static ItemManager<Event> getSampleEventList() {
        EventManager sampleEv = new EventManager();
        for (Event sampleEvent : getSampleEvents()) {
            sampleEv.addItem(sampleEvent);
        }
        return sampleEv;
    }


    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

}
