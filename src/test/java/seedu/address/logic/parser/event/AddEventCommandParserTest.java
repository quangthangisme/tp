package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ContactCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.ContactCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.EventCommandTestUtil.END_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_END_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_LOCATION_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_START_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_TAG_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_TAG_DESC_MULTIPLE;
import static seedu.address.logic.commands.EventCommandTestUtil.LOCATION_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.NAME_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.START_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.TAG_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.TAG_DESC_CRYING_MULTIPLE;
import static seedu.address.logic.commands.EventCommandTestUtil.TAG_DESC_EMPTY;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_END_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_LOCATION_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_NAME_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_START_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING_2;
import static seedu.address.logic.commands.TodoCommandTestUtil.DEADLINE_DESC_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.LOCATION_DESC_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.NAME_DESC_GRADING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;
import static seedu.address.testutil.TypicalEvents.CRYING;
import static seedu.address.testutil.TypicalEvents.CRYING_MULTIPLE_TAG;
import static seedu.address.testutil.TypicalEvents.CRYING_WITH_TAG;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.create.AddEventCommand;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

class AddEventCommandParserTest {

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING
                        + LOCATION_DESC_CRYING, new AddEventCommand(CRYING));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING
                        + LOCATION_DESC_CRYING + TAG_DESC_EMPTY,
                new AddEventCommand(CRYING));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_CRYING + " with tag" + START_DESC_CRYING + END_DESC_CRYING
                        + LOCATION_DESC_CRYING + TAG_DESC_CRYING, new AddEventCommand(CRYING_WITH_TAG));

        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_CRYING + " with multiple tags" + START_DESC_CRYING + END_DESC_CRYING
                        + LOCATION_DESC_CRYING + TAG_DESC_CRYING_MULTIPLE,
                new AddEventCommand(CRYING_MULTIPLE_TAG));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedEventString = NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING
                + LOCATION_DESC_CRYING + TAG_DESC_CRYING_MULTIPLE;

        // multiple names
        assertParseFailure(parser, NAME_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME_LONG));

        // multiple start times
        assertParseFailure(parser, START_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_START_LONG));

        // multiple end times
        assertParseFailure(parser, END_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_END_LONG));

        // multiple locations
        assertParseFailure(parser, LOCATION_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_LOCATION_LONG));

        // multiple tags
        assertParseFailure(parser, TAG_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_TAG_LONG));

        // multiple fields repeated
        assertParseFailure(parser, NAME_DESC_CRYING + END_DESC_CRYING + LOCATION_DESC_CRYING
                        + TAG_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME_LONG,
                        PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_TAG_LONG));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME_LONG));

        // invalid start
        assertParseFailure(parser, INVALID_EVENT_START_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_START_LONG));

        // invalid end
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_END_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_END_LONG));

        // invalid location
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_LOCATION_LONG));

        // invalid tag
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_TAG_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_TAG_LONG));

        // valid value followed by invalid value
        // invalid name
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_NAME_LONG));

        // invalid start
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_START_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_START_LONG));

        // invalid end
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_END_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_END_LONG));

        // invalid location
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_LOCATION_LONG));

        // invalid tag
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_TAG_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EVENT_TAG_LONG));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, LOCATION_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING,
                expectedMessage);

        // missing start prefix
        assertParseFailure(parser, NAME_DESC_CRYING + LOCATION_DESC_CRYING + END_DESC_CRYING,
                expectedMessage);

        // missing end prefix
        assertParseFailure(parser, NAME_DESC_CRYING + LOCATION_DESC_CRYING + END_DESC_CRYING,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_NAME_CRYING + " " + VALID_START_CRYING + " " + VALID_END_CRYING
                        + " " + VALID_LOCATION_CRYING + " " + VALID_TAG_CRYING + " " + VALID_TAG_CRYING_2,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + LOCATION_DESC_CRYING + END_DESC_CRYING
                + START_DESC_CRYING, Name.MESSAGE_CONSTRAINTS);

        // invalid start
        assertParseFailure(parser, NAME_DESC_CRYING + LOCATION_DESC_CRYING + END_DESC_CRYING
                + INVALID_EVENT_START_DESC, Datetime.MESSAGE_CONSTRAINTS);

        // invalid end
        assertParseFailure(parser, NAME_DESC_CRYING + LOCATION_DESC_CRYING + START_DESC_CRYING
                + INVALID_EVENT_END_DESC, Datetime.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser,
                NAME_DESC_CRYING + INVALID_EVENT_LOCATION_DESC + START_DESC_CRYING
                        + END_DESC_CRYING, Location.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_CRYING + LOCATION_DESC_CRYING + END_DESC_CRYING
                + START_DESC_CRYING + INVALID_EVENT_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, NAME_DESC_CRYING + LOCATION_DESC_CRYING + START_DESC_CRYING
                + END_DESC_CRYING + INVALID_EVENT_TAG_DESC_MULTIPLE, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_GRADING + DEADLINE_DESC_GRADING
                        + LOCATION_DESC_GRADING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
