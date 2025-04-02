package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EventCommandTestUtil.END_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_END_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_LINKED_CONTACT_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_LINKED_CONTACT_INDEX;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_LOCATION_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_START_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_TAG_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.LINKED_CONTACTS_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.LOCATION_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.NAME_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.START_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.TAG_DESC_CRYING_MULTIPLE;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_END_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_LOCATION_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_NAME_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_START_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.update.EditEventCommand;
import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.model.event.Attendance;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_EVENT_TAG_LONG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private final EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_CRYING,
                String.format(MESSAGE_INVALID_INDEX, VALID_NAME_CRYING));

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_CRYING, String.format(MESSAGE_INVALID_INDEX,
                "-5"));

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_CRYING, String.format(MESSAGE_INVALID_INDEX,
                "0"));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_INDEX, "1 some random string"));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", String.format(MESSAGE_INVALID_INDEX, "1 z/ " +
                "string"));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EVENT_START_DESC,
                Datetime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EVENT_END_DESC,
                Datetime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EVENT_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EVENT_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_EVENT_LINKED_CONTACT_DESC,
                String.format(MESSAGE_INVALID_INDEX, INVALID_EVENT_LINKED_CONTACT_INDEX));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC
                        + INVALID_EVENT_START_DESC + INVALID_EVENT_TAG_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_CRYING
                + LOCATION_DESC_CRYING
                + START_DESC_CRYING
                + END_DESC_CRYING
                + TAG_DESC_CRYING_MULTIPLE
                + LINKED_CONTACTS_DESC;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_CRYING)
                .withEventStartTime(VALID_START_CRYING)
                .withEventEndTime(VALID_END_CRYING)
                .withLocation(VALID_LOCATION_CRYING)
                .withTags(VALID_TAG_CRYING_2, VALID_TAG_CRYING)
                .withAttendance(new Attendance()).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_CRYING + LOCATION_DESC_CRYING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_CRYING)
                .withLocation(VALID_LOCATION_CRYING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_CRYING;
        EditEventDescriptor descriptor =
                new EditEventDescriptorBuilder().withName(VALID_NAME_CRYING)
                .build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_CRYING;
        descriptor = new EditEventDescriptorBuilder().withLocation(VALID_LOCATION_CRYING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start
        userInput = targetIndex.getOneBased() + START_DESC_CRYING;
        descriptor =
                new EditEventDescriptorBuilder().withEventStartTime(VALID_START_CRYING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end
        userInput = targetIndex.getOneBased() + END_DESC_CRYING;
        descriptor = new EditEventDescriptorBuilder().withEventEndTime(VALID_END_CRYING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tag
        userInput = targetIndex.getOneBased() + TAG_DESC_CRYING_MULTIPLE;
        descriptor = new EditEventDescriptorBuilder().withTags(VALID_TAG_CRYING_2, VALID_TAG_CRYING)
                .build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // attendance
        userInput = targetIndex.getOneBased() + LINKED_CONTACTS_DESC;
        descriptor = new EditEventDescriptorBuilder().withAttendance(new Attendance()).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedValue_failure()

        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()
                + INVALID_EVENT_LOCATION_DESC
                + INVALID_EVENT_NAME_DESC
                + INVALID_EVENT_LOCATION_DESC
                + INVALID_EVENT_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME_LONG,
                        PREFIX_EVENT_LOCATION_LONG));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTags().build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
