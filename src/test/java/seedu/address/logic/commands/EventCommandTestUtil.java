package seedu.address.logic.commands;

import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_LONG;

/**
 * Contains helper methods for testing event commands.
 */
public class EventCommandTestUtil {

    public static final String VALID_NAME_MEETING = "MEETING at ScHoOl";
    public static final String VALID_START_MEETING = "25-12-31 23:59";
    public static final String VALID_END_MEETING = "26-01-01 00:59";
    public static final String VALID_LOCATION_MEETING = "Prof office";
    public static final String VALID_TAG_MEETING = "why????";

    public static final String VALID_NAME_CRYING = "Crying? over? PhD?!@#$%^&*()";
    public static final String VALID_START_CRYING = "25-12-31 23:59";
    public static final String VALID_END_CRYING = "26-01-01 00:59";
    public static final String VALID_LOCATION_CRYING = "Prof-office!!!";
    public static final String VALID_TAG_CRYING = "sad";
    public static final String VALID_TAG_CRYING_2 = "better-than-you";

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
    public static final String TAG_DESC_CRYING_MULTIPLE =
            " " + PREFIX_EVENT_TAG_LONG + VALID_TAG_CRYING + " " + VALID_TAG_CRYING_2;
    public static final String TAG_DESC_EMPTY =
            " " + PREFIX_EVENT_TAG_LONG;
    public static final String LINKED_CONTACTS_DESC =
            " " + PREFIX_TODO_LINKED_CONTACT_LONG + "1 2";

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME_LONG + "LIL -";
    // Not datetime
    public static final String INVALID_EVENT_START_DESC = " " + PREFIX_EVENT_START_LONG + "aaaa";
    // incorrect format
    public static final String INVALID_EVENT_END_DESC = " " + PREFIX_EVENT_END_LONG + "23:59 25-12-31";
    public static final String INVALID_EVENT_LOCATION_DESC = " " + PREFIX_EVENT_LOCATION_LONG
            + "MMMM - MMMM";
    public static final String INVALID_EVENT_TAG_DESC = " " + PREFIX_EVENT_TAG_LONG + "-hubby";
    public static final String INVALID_EVENT_TAG_DESC_MULTIPLE =
            " " + PREFIX_EVENT_TAG_LONG + "hello -hubby";
    public static final String INVALID_EVENT_LINKED_CONTACT_INDEX = "yay";
    public static final String INVALID_EVENT_LINKED_CONTACT_DESC =
            " " + PREFIX_TODO_LINKED_CONTACT_LONG + INVALID_EVENT_LINKED_CONTACT_INDEX;
}
