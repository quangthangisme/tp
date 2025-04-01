package seedu.address.testutil;

import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BOB;

import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalEvents {
    // Examples. You should add mmore on your own.
    public static final Event MEETING = new EventBuilder()
            .withName("meeting")
            .withStart("24-12-31 23:59")
            .withEnd("25-01-01 23:59")
            .withLocation("bedroom home")
            .withAttendance(ALICE, BOB)
            .withTags("good", "boring").build();
}
