package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.Group;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Id("A0123456X"), new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Course("CS1010S"), new Group("T01"),
                getTagSet("friends")),
            new Person(new Id("A1234567Y"), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new Course("CS1010S"), new Group("R01"),
                getTagSet("colleagues", "friends")),
            new Person(new Id("A2345678Z"), new Name("Charlotte Oliveiro"),
                new Phone("93210283"), new Email("charlotte@example.com"),
                new Course("CS2103"), new Group("F08"),
                getTagSet("neighbours")),
            new Person(new Id("A3456789G"), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"),
                new Course("CS2030"), new Group("F10"),
                getTagSet("family")),
            new Person(new Id("A4567890H"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"),
                new Course("CS2040"), new Group("T10"),
                getTagSet("classmates")),
            new Person(new Id("A5678901K"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"),
                new Course("CS1010S"), new Group("T28"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
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
