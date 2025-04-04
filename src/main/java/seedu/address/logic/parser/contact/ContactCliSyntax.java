package seedu.address.logic.parser.contact;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple contact commands.
 */
public class ContactCliSyntax {

    /* Long Prefix definitions */
    public static final Prefix PREFIX_CONTACT_NAME_LONG = new Prefix("--name ");
    public static final Prefix PREFIX_CONTACT_EMAIL_LONG = new Prefix("--email ");
    public static final Prefix PREFIX_CONTACT_ID_LONG = new Prefix("--id ");
    public static final Prefix PREFIX_CONTACT_COURSE_LONG = new Prefix("--course ");
    public static final Prefix PREFIX_CONTACT_GROUP_LONG = new Prefix("--group ");
    public static final Prefix PREFIX_CONTACT_TAG_LONG = new Prefix("--tag ");

    /* Short Prefix definitions */
    public static final Prefix PREFIX_CONTACT_NAME_SHORT = new Prefix("-n ");
    public static final Prefix PREFIX_CONTACT_EMAIL_SHORT = new Prefix("-e ");
    public static final Prefix PREFIX_CONTACT_ID_SHORT = new Prefix("-i ");
    public static final Prefix PREFIX_CONTACT_COURSE_SHORT = new Prefix("-c ");
    public static final Prefix PREFIX_CONTACT_GROUP_SHORT = new Prefix("-g ");
    public static final Prefix PREFIX_CONTACT_TAG_SHORT = new Prefix("-t ");

    /* PrefixAlias definitions */
    public static final PrefixAlias PREFIX_ALIAS_CONTACT_NAME = new PrefixAlias(PREFIX_CONTACT_NAME_LONG,
            PREFIX_CONTACT_NAME_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_CONTACT_EMAIL = new PrefixAlias(PREFIX_CONTACT_EMAIL_LONG,
            PREFIX_CONTACT_EMAIL_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_CONTACT_ID = new PrefixAlias(PREFIX_CONTACT_ID_LONG,
            PREFIX_CONTACT_ID_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_CONTACT_COURSE = new PrefixAlias(PREFIX_CONTACT_COURSE_LONG,
            PREFIX_CONTACT_COURSE_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_CONTACT_GROUP = new PrefixAlias(PREFIX_CONTACT_GROUP_LONG,
            PREFIX_CONTACT_GROUP_SHORT);
    public static final PrefixAlias PREFIX_ALIAS_CONTACT_TAG = new PrefixAlias(PREFIX_CONTACT_TAG_LONG,
            PREFIX_CONTACT_TAG_SHORT);

}
