package seedu.address.logic.parser.event;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple event commands.
 */
public class EventCliSyntax {

    /* Long prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME_LONG = new Prefix("--name ");
    public static final Prefix PREFIX_EVENT_START_LONG = new Prefix("--start ");
    public static final Prefix PREFIX_EVENT_END_LONG = new Prefix("--end ");
    public static final Prefix PREFIX_EVENT_LOCATION_LONG = new Prefix("--location ");
    public static final Prefix PREFIX_EVENT_TAG_LONG = new Prefix("--tag ");
    public static final Prefix PREFIX_EVENT_LINKED_CONTACT_LONG = new Prefix("--contact ");

    /* Short prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME_SHORT = new Prefix("-n ");
    public static final Prefix PREFIX_EVENT_START_SHORT = new Prefix("-s ");
    public static final Prefix PREFIX_EVENT_END_SHORT = new Prefix("-e ");
    public static final Prefix PREFIX_EVENT_LOCATION_SHORT = new Prefix("-l ");
    public static final Prefix PREFIX_EVENT_TAG_SHORT = new Prefix("-t ");
    public static final Prefix PREFIX_EVENT_LINKED_CONTACT_SHORT = new Prefix("-c ");

    /* PrefixAlias definitions */
    public static final PrefixAlias PREFIX_ALIAS_EVENT_NAME = new PrefixAlias(
            PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_NAME_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_EVENT_START = new PrefixAlias(
            PREFIX_EVENT_START_LONG, PREFIX_EVENT_START_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_EVENT_END = new PrefixAlias(
            PREFIX_EVENT_END_LONG, PREFIX_EVENT_END_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_EVENT_LOCATION = new PrefixAlias(
            PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_LOCATION_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_EVENT_TAG = new PrefixAlias(
            PREFIX_EVENT_TAG_LONG, PREFIX_EVENT_TAG_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_EVENT_LINKED_CONTACT = new PrefixAlias(
            PREFIX_EVENT_LINKED_CONTACT_LONG, PREFIX_EVENT_LINKED_CONTACT_SHORT);
}
