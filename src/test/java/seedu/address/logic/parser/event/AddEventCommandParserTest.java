package seedu.address.logic.parser.event;

import static seedu.address.logic.commands.ContactCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.EventCommandTestUtil.END_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_END_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_LOCATION_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.INVALID_EVENT_START_DESC;
import static seedu.address.logic.commands.EventCommandTestUtil.LOCATION_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.NAME_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.START_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.TAG_DESC_CRYING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.CRYING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.create.AddEventCommand;

class AddEventCommandParserTest {

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING + LOCATION_DESC_CRYING
                        + TAG_DESC_CRYING, new AddEventCommand(CRYING));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedEventString = NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING + LOCATION_DESC_CRYING;

        // multiple names
        assertParseFailure(parser, NAME_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_NAME_LONG));

        // multiple start times
        assertParseFailure(parser, START_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_START_LONG));

        // multiple end times
        assertParseFailure(parser, END_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_END_LONG));

        // multiple locations
        assertParseFailure(parser, LOCATION_DESC_CRYING + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_LOCATION_LONG));

        // multiple fields repeated
        assertParseFailure(parser, NAME_DESC_CRYING + END_DESC_CRYING + LOCATION_DESC_CRYING
                        + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_NAME_LONG,
                        EventCliSyntax.PREFIX_EVENT_END_LONG, EventCliSyntax.PREFIX_EVENT_LOCATION_LONG));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_NAME_LONG));

        // invalid start
        assertParseFailure(parser, INVALID_EVENT_START_DESC + validExpectedEventString,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_START_LONG));

        // invalid end
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_END_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_END_LONG));

        // invalid location
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_LOCATION_LONG));

        // valid value followed by invalid value
        // invalid name
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_NAME_LONG));

        // invalid start
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_START_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_START_LONG));

        // invalid end
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_END_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_END_LONG));

        // invalid location
        assertParseFailure(parser, validExpectedEventString + INVALID_EVENT_LOCATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(EventCliSyntax.PREFIX_EVENT_LOCATION_LONG));
    }

}
