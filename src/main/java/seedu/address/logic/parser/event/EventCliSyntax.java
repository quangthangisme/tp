package seedu.address.logic.parser.event;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple event commands.
 */
public class EventCliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("s/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("e/");
    public static final Prefix PREFIX_EVENT_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_LINKED_PERSON_INDEX = new Prefix("p/");
    public static final Prefix PREFIX_EVENT_TAG = new Prefix("t/");
}
