package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditContactDescriptor;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.EditContactDescriptorBuilder;

/**
 * A utility class to help with building EditContactDescriptor objects.
 */
public class ContactCommandTestUtil {

    public static final String VALID_ID_AMY = "B0000000001";
    public static final String ID_DESC_AMY = " " + PREFIX_CONTACT_ID_LONG + VALID_ID_AMY;
    public static final String VALID_ID_BOB = "B0000000002";
    public static final String ID_DESC_BOB = " " + PREFIX_CONTACT_ID_LONG + VALID_ID_BOB;
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String NAME_DESC_AMY = " " + PREFIX_CONTACT_NAME_LONG + VALID_NAME_AMY;
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String NAME_DESC_BOB = " " + PREFIX_CONTACT_NAME_LONG + VALID_NAME_BOB;
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String EMAIL_DESC_AMY = " " + PREFIX_CONTACT_EMAIL_LONG + VALID_EMAIL_AMY;
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String EMAIL_DESC_BOB = " " + PREFIX_CONTACT_EMAIL_LONG + VALID_EMAIL_BOB;
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_CONTACT_TAG_LONG + VALID_TAG_HUSBAND;
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String TAG_DESC_FRIEND_HUSBAND =
            " " + PREFIX_CONTACT_TAG_LONG + VALID_TAG_FRIEND + " " + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_CONTACT_TAG_LONG + VALID_TAG_FRIEND;
    public static final String VALID_COURSE_AMY = "CS50";
    public static final String COURSE_DESC_AMY = " " + PREFIX_CONTACT_COURSE_LONG + VALID_COURSE_AMY;
    public static final String VALID_COURSE_BOB = "CS-60";
    public static final String COURSE_DESC_BOB = " " + PREFIX_CONTACT_COURSE_LONG + VALID_COURSE_BOB;
    public static final String VALID_GROUP_AMY = "T0-9";
    public static final String GROUP_DESC_AMY = " " + PREFIX_CONTACT_GROUP_LONG + VALID_GROUP_AMY;
    public static final String VALID_GROUP_BOB = "T08";
    public static final String GROUP_DESC_BOB = " " + PREFIX_CONTACT_GROUP_LONG + VALID_GROUP_BOB;
    public static final String INVALID_NAME_DESC = " " + PREFIX_CONTACT_NAME_LONG + "-James&";
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_CONTACT_EMAIL_LONG + "bob!yahoo";
    public static final String INVALID_TAG_DESC = " " + PREFIX_CONTACT_TAG_LONG + "-hubby*";
    public static final String INVALID_TAG_HUSBAND_DESC = " " + PREFIX_CONTACT_TAG_LONG
            + "husband -hubby*";
    public static final String INVALID_COURSE_DESC = " " + PREFIX_CONTACT_COURSE_LONG
            + "CS -hubby*";
    public static final String INVALID_GROUP_DESC = " " + PREFIX_CONTACT_GROUP_LONG
            + "-T^^";
    public static final String INVALID_ID_DESC = " " + PREFIX_CONTACT_ID_LONG
            + "-T ^???^";
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final EditContactDescriptor DESC_AMY;
    public static final EditContactDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                                                    .withEmail(VALID_EMAIL_AMY)
                                                    .withTags(VALID_TAG_FRIEND)
                                                    .build();
        DESC_BOB = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB)
                                                    .withEmail(VALID_EMAIL_BOB)
                                                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                                                    .build();
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showContactAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased()
                < model.getContactManagerAndList().getFilteredItemsList().size());

        Contact contact = model.getContactManagerAndList().getFilteredItemsList()
                .get(targetIndex.getZeroBased());
        model.getContactManagerAndList().updateFilteredItemsList(contact::equals);

        assertEquals(1, model.getContactManagerAndList().getFilteredItemsList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the contact.
     */
    public static void showContact(Model model, Contact contact) {
        model.getContactManagerAndList().updateFilteredItemsList(contact::equals);
        assertEquals(1, model.getContactManagerAndList().getFilteredItemsList().size());
    }
}
