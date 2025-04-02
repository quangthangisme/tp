package seedu.address.logic.parser.event;

import static seedu.address.logic.commands.ContactCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.EventCommandTestUtil.END_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.LOCATION_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.NAME_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.START_DESC_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.TAG_DESC_CRYING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.CRYING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.create.AddEventCommand;
import seedu.address.model.event.Event;

class AddEventCommandParserTest {

    private final AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = CRYING;

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE
                        + NAME_DESC_CRYING + START_DESC_CRYING + END_DESC_CRYING + LOCATION_DESC_CRYING
                        + TAG_DESC_CRYING,
                new AddEventCommand(expectedEvent));
    }

}
