package seedu.address.logic.parser.contact;

import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_SHORT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_SHORT;

import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax alias definitions for {@code Contact}.
 */
public class ContactCliAlias {
    public static final PrefixAlias CONTACT_COURSE_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_CONTACT_COURSE_LONG, PREFIX_CONTACT_COURSE_SHORT);
    public static final PrefixAlias CONTACT_EMAIL_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_EMAIL_SHORT);
    public static final PrefixAlias CONTACT_GROUP_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_CONTACT_GROUP_LONG, PREFIX_CONTACT_GROUP_SHORT);
    public static final PrefixAlias CONTACT_ID_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_CONTACT_ID_LONG, PREFIX_CONTACT_ID_SHORT);
    public static final PrefixAlias CONTACT_NAME_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_CONTACT_NAME_LONG, PREFIX_CONTACT_NAME_SHORT);
    public static final PrefixAlias CONTACT_TAG_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_CONTACT_TAG_LONG, PREFIX_CONTACT_TAG_SHORT);
}
