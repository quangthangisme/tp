package seedu.address.logic.parser.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TodoCommandTestUtil.DEADLINE_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_DEADLINE_DESC_NOT_DATETIME;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_LINKED_CONTACT_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_LINKED_CONTACT_INDEX;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_LOCATION_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_NAME_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_STATUS;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_STATUS_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.INVALID_TODO_TAG_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.LINKED_CONTACTS_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.LOCATION_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.NAME_DESC_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.STATUS_DESC;
import static seedu.address.logic.commands.TodoCommandTestUtil.TAG_DESC_REPORT_MULTIPLE;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_DEADLINE_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_LOCATION_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_STATUS;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_TAG_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_TAG_REPORT_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_BOOLEAN;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.update.EditTodoCommand;
import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.testutil.EditTodoDescriptorBuilder;

public class EditTodoCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TODO_TAG_LONG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTodoCommand.MESSAGE_USAGE);

    private final EditTodoCommandParser parser = new EditTodoCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_REPORT,
                String.format(MESSAGE_INVALID_INDEX, VALID_NAME_REPORT));

        // no field specified
        assertParseFailure(parser, "1", EditTodoCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_REPORT, String.format(MESSAGE_INVALID_INDEX, "-5"));

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_REPORT, String.format(MESSAGE_INVALID_INDEX, "0"));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string",
                String.format(MESSAGE_INVALID_INDEX, "1 some random string"));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", String.format(MESSAGE_INVALID_INDEX, "1 z/ " +
                "string"));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TODO_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TODO_DEADLINE_DESC_NOT_DATETIME,
                Datetime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT,
                Datetime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TODO_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TODO_LOCATION_DESC, Location.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TODO_LINKED_CONTACT_DESC,
                String.format(MESSAGE_INVALID_INDEX, INVALID_TODO_LINKED_CONTACT_INDEX));
        assertParseFailure(parser, "1" + INVALID_TODO_STATUS_DESC,
                String.format(MESSAGE_INVALID_BOOLEAN, INVALID_TODO_STATUS));

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TODO_NAME_DESC
                        + INVALID_TODO_DEADLINE_DESC_NOT_DATETIME + INVALID_TODO_TAG_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_REPORT
                + LOCATION_DESC_REPORT
                + DEADLINE_DESC_REPORT
                + STATUS_DESC
                + TAG_DESC_REPORT_MULTIPLE
                + LINKED_CONTACTS_DESC;

        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder()
                .withName(VALID_NAME_REPORT)
                .withDeadline(VALID_DEADLINE_REPORT)
                .withLocation(VALID_LOCATION_REPORT)
                .withStatus(VALID_STATUS)
                .withTags(VALID_TAG_REPORT_2, VALID_TAG_REPORT)
                .withContacts().build();
        EditTodoCommand expectedCommand = new EditTodoCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + NAME_DESC_REPORT + LOCATION_DESC_REPORT;

        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder()
                .withName(VALID_NAME_REPORT)
                .withLocation(VALID_LOCATION_REPORT).build();
        EditTodoCommand expectedCommand = new EditTodoCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + NAME_DESC_REPORT;
        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder().withName(VALID_NAME_REPORT)
                .build();
        EditTodoCommand expectedCommand = new EditTodoCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_REPORT;
        descriptor = new EditTodoDescriptorBuilder().withLocation(VALID_LOCATION_REPORT).build();
        expectedCommand = new EditTodoCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = targetIndex.getOneBased() + DEADLINE_DESC_REPORT;
        descriptor = new EditTodoDescriptorBuilder().withDeadline(VALID_DEADLINE_REPORT).build();
        expectedCommand = new EditTodoCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC;
        descriptor = new EditTodoDescriptorBuilder().withStatus(VALID_STATUS).build();
        expectedCommand = new EditTodoCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tag
        userInput = targetIndex.getOneBased() + TAG_DESC_REPORT_MULTIPLE;
        descriptor = new EditTodoDescriptorBuilder().withTags(VALID_TAG_REPORT_2, VALID_TAG_REPORT)
                .build();
        expectedCommand = new EditTodoCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // contacts
        userInput = targetIndex.getOneBased() + LINKED_CONTACTS_DESC;
        descriptor = new EditTodoDescriptorBuilder().withContacts().build();
        expectedCommand = new EditTodoCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedValue_failure()

        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()
                + INVALID_TODO_LOCATION_DESC
                + INVALID_TODO_NAME_DESC
                + INVALID_TODO_LOCATION_DESC
                + INVALID_TODO_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TODO_NAME_LONG,
                        PREFIX_TODO_LOCATION_LONG));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder().withTags().build();
        EditTodoCommand expectedCommand = new EditTodoCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
