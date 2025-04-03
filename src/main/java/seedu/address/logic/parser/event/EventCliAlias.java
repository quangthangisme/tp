package seedu.address.logic.parser.event;

import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_SHORT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_SHORT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_SHORT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_SHORT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_SHORT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_SHORT;

import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax alias definitions for {@code Event}.
 */
public class EventCliAlias {
    public static final PrefixAlias EVENT_NAME_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_NAME_SHORT);
    public static final PrefixAlias EVENT_START_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_EVENT_START_LONG, PREFIX_EVENT_START_SHORT);
    public static final PrefixAlias EVENT_END_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_EVENT_END_LONG, PREFIX_EVENT_END_SHORT);
    public static final PrefixAlias EVENT_LOCATION_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_LOCATION_SHORT);
    public static final PrefixAlias EVENT_TAG_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_EVENT_TAG_LONG, PREFIX_EVENT_TAG_SHORT);
    public static final PrefixAlias EVENT_LINKED_CONTACT_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_EVENT_LINKED_CONTACT_LONG, PREFIX_EVENT_LINKED_CONTACT_SHORT);
}
