package seedu.address.logic.parser.todo;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple todo commands.
 */
public class TodoCliSyntax {

    /* Long prefix definitions */
    public static final Prefix PREFIX_TODO_NAME_LONG = new Prefix("--name ");
    public static final Prefix PREFIX_TODO_DEADLINE_LONG = new Prefix("--deadline ");
    public static final Prefix PREFIX_TODO_LOCATION_LONG = new Prefix("--location ");
    public static final Prefix PREFIX_TODO_TAG_LONG = new Prefix("--tag ");
    public static final Prefix PREFIX_TODO_STATUS_LONG = new Prefix("--status ");
    public static final Prefix PREFIX_TODO_LINKED_CONTACT_LONG = new Prefix("--contact ");

    /* Short prefix definitions */
    public static final Prefix PREFIX_TODO_NAME_SHORT = new Prefix("-n ");
    public static final Prefix PREFIX_TODO_DEADLINE_SHORT = new Prefix("-d ");
    public static final Prefix PREFIX_TODO_LOCATION_SHORT = new Prefix("-l ");
    public static final Prefix PREFIX_TODO_TAG_SHORT = new Prefix("-t ");
    public static final Prefix PREFIX_TODO_STATUS_SHORT = new Prefix("-s ");
    public static final Prefix PREFIX_TODO_LINKED_CONTACT_SHORT = new Prefix("-c ");

    /* PrefixAlias definitions */
    public static final PrefixAlias PREFIX_ALIAS_TODO_NAME = new PrefixAlias(
            PREFIX_TODO_NAME_LONG, PREFIX_TODO_NAME_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_TODO_DEADLINE = new PrefixAlias(
            PREFIX_TODO_DEADLINE_LONG, PREFIX_TODO_DEADLINE_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_TODO_LOCATION = new PrefixAlias(
            PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_LOCATION_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_TODO_TAG = new PrefixAlias(
            PREFIX_TODO_TAG_LONG, PREFIX_TODO_TAG_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_TODO_STATUS = new PrefixAlias(
            PREFIX_TODO_STATUS_LONG, PREFIX_TODO_STATUS_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_TODO_LINKED_CONTACT = new PrefixAlias(
            PREFIX_TODO_LINKED_CONTACT_LONG, PREFIX_TODO_LINKED_CONTACT_SHORT);
}
