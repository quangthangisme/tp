package seedu.address.testutil;

import static seedu.address.logic.commands.EventCommandTestUtil.VALID_END_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_END_MEETING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_LOCATION_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_LOCATION_MEETING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_NAME_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_NAME_MEETING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_START_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_START_MEETING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING_2;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_MEETING;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.CARL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalEvents {
    // Examples. You should add more on your own.

    public static final Event STUFF_EVENT = new EventBuilder()
            .withName("stuff")
            .withStart("28-02-28 23:29")
            .withEnd("28-02-29 23:29")
            .withLocation("NUS").build();

    public static final Event STUFF_EVENT_2 = new EventBuilder()
            .withName("stuff2")
            .withStart("28-02-28 23:29")
            .withEnd("28-02-29 23:29")
            .withLocation("NUS").build();

    public static final Event CRYING = new EventBuilder()
            .withName(VALID_NAME_CRYING)
            .withStart(VALID_START_CRYING)
            .withEnd(VALID_END_CRYING)
            .withLocation(VALID_LOCATION_CRYING)
            .withTags(VALID_TAG_CRYING).build();

    public static final Event CRYING_NO_TAG = new EventBuilder()
            .withName(VALID_NAME_CRYING)
            .withStart(VALID_START_CRYING)
            .withEnd(VALID_END_CRYING)
            .withLocation(VALID_LOCATION_CRYING).build();

    public static final Event CRYING_MULTIPLE_TAG = new EventBuilder()
            .withName(VALID_NAME_CRYING)
            .withStart(VALID_START_CRYING)
            .withEnd(VALID_END_CRYING)
            .withLocation(VALID_LOCATION_CRYING)
            .withTags(VALID_TAG_CRYING, VALID_TAG_CRYING_2).build();

    public static final Event MEETING = new EventBuilder()
            .withName(VALID_NAME_MEETING)
            .withStart(VALID_START_MEETING)
            .withEnd(VALID_END_MEETING)
            .withLocation(VALID_LOCATION_MEETING)
            .withAttendance(ALICE, CARL)
            .withTags(VALID_TAG_MEETING).build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code EventList} with all the typical events.
     */
    public static EventManager getTypicalEventList() {
        EventManager el = new EventManager();
        for (Event event : getTypicalEvents()) {
            el.addItem(event);
        }
        return el;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(CRYING, MEETING));
    }
}
