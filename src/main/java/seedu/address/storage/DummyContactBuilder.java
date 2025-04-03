package seedu.address.storage;

import java.util.Set;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * Builds a dummy {@link Contact} with a given ID.
 */
public class DummyContactBuilder {
    private static final Name DUMMY_NAME = new Name("a");
    private static final Email DUMMY_EMAIL = new Email("a@a.com");
    private static final Course DUMMY_COURSE = new Course("a");
    private static final Group DUMMY_GROUP = new Group("a");
    private static final Set<Tag> DUMMY_TAGS = Set.of();

    /**
     * Builds a dummy {@link Contact} with a given ID.
     */
    public static Contact build(Id id) {
        return new Contact(id, DUMMY_NAME, DUMMY_EMAIL, DUMMY_COURSE, DUMMY_GROUP, DUMMY_TAGS);
    }
}
