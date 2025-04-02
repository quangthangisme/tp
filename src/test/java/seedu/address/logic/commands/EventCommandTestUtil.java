package seedu.address.logic.commands;

import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

/**
 * Contains helper methods for testing event commands.
 */
public class EventCommandTestUtil {

    public static final String VALID_NAME_MEETING = "MEETING at ScHoOl";
    public static final String VALID_START_MEETING = "25-12-31 23:59";
    public static final String VALID_END_MEETING = "26-01-01 00:59";
    public static final String VALID_LOCATION_MEETING = "Prof office";
    public static final String VALID_TAG_MEETING = "why????";

    public static final String VALID_NAME_CRYING = "Crying over PhD";
    public static final String VALID_START_CRYING = "25-12-31 23:59";
    public static final String VALID_END_CRYING = "26-01-01 00:59";
    public static final String VALID_LOCATION_CRYING = "Prof office";
    public static final String VALID_TAG_CRYING = "sad";

    public static final String NAME_DESC_MEETING = " " + PREFIX_EVENT_NAME_LONG + VALID_NAME_MEETING;
    public static final String START_DESC_MEETING = " " + PREFIX_EVENT_START_LONG + VALID_START_MEETING;
    public static final String END_DESC_MEETING = " " + PREFIX_EVENT_END_LONG + VALID_END_MEETING;
    public static final String LOCATION_DESC_MEETING = " " + PREFIX_EVENT_LOCATION_LONG + VALID_LOCATION_MEETING;
    public static final String TAG_DESC_MEETING = " " + PREFIX_EVENT_TAG_LONG + VALID_TAG_MEETING;
    public static final String NAME_DESC_CRYING = " " + PREFIX_EVENT_NAME_LONG + VALID_NAME_CRYING;
    public static final String START_DESC_CRYING = " " + PREFIX_EVENT_START_LONG + VALID_START_CRYING;
    public static final String END_DESC_CRYING = " " + PREFIX_EVENT_END_LONG + VALID_END_CRYING;
    public static final String LOCATION_DESC_CRYING = " " + PREFIX_EVENT_LOCATION_LONG + VALID_LOCATION_CRYING;
    public static final String TAG_DESC_CRYING = " " + PREFIX_EVENT_TAG_LONG + VALID_TAG_CRYING;

    // & not allowed
    public static final String INVALID_NAME_EVENT = " " + PREFIX_EVENT_NAME_LONG + "Stuff&stuff";
    public static final String INVALID_NOT_DATETIME = " " + PREFIX_EVENT_START_LONG + "aaaa";
    public static final String INVALID_DATETIME_INCORRECT_FORMAT = " " + PREFIX_EVENT_END_LONG + "23:59 25-12-31";
    // empty
    public static final String INVALID_LOCATION_EVENT = " " + PREFIX_EVENT_LOCATION_LONG + "";
    // * not allowed
    public static final String INVALID_TAG_EVENT = " " + PREFIX_EVENT_TAG_LONG + "hubby*";


}
