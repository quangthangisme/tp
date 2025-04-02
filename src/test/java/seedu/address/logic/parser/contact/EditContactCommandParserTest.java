package seedu.address.logic.parser.contact;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ContactCommandTestUtil.COURSE_DESC_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.GROUP_DESC_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.INVALID_COURSE_DESC;
import static seedu.address.logic.commands.ContactCommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.ContactCommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.ContactCommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.ContactCommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.ContactCommandTestUtil.INVALID_TAG_HUSBAND_DESC;
import static seedu.address.logic.commands.ContactCommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.ContactCommandTestUtil.TAG_DESC_FRIEND_HUSBAND;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_COURSE_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_GROUP_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.update.EditContactCommand;
import seedu.address.logic.commands.update.EditContactDescriptor;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.testutil.EditContactDescriptorBuilder;

public class EditContactCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_CONTACT_TAG_LONG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContactCommand.MESSAGE_USAGE);

    private final EditContactCommandParser parser = new EditContactCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY,
                String.format(MESSAGE_INVALID_INDEX, VALID_NAME_AMY));

        // no field specified
        assertParseFailure(parser, "1", EditContactCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, String.format(MESSAGE_INVALID_INDEX, "-5"));

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, String.format(MESSAGE_INVALID_INDEX, "0"));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_INDEX, "1 some random string"));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", String.format(MESSAGE_INVALID_INDEX, "1 z/ string"));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TAG_HUSBAND_DESC, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_ID_DESC, Id.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_COURSE_DESC, Course.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_GROUP_DESC, Group.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND_HUSBAND + EMAIL_DESC_AMY
                + NAME_DESC_AMY + COURSE_DESC_AMY + ID_DESC_AMY + GROUP_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withCourse(VALID_COURSE_AMY)
                .withGroup(VALID_GROUP_AMY)
                .withId(VALID_ID_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + EMAIL_DESC_AMY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditContactDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditContactCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedValue_failure()

        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_EMAIL_DESC + INVALID_NAME_DESC + INVALID_EMAIL_DESC
            + INVALID_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACT_EMAIL_LONG, PREFIX_CONTACT_NAME_LONG));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withTags().build();
        EditContactCommand expectedCommand = new EditContactCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
