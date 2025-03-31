package seedu.address.logic.parser.todo;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.logic.commands.update.MarkTodoAsDoneCommand;
import seedu.address.model.todo.TodoStatus;

public class MarkTodoAsDoneCommandParserTest {

    private final MarkTodoAsDoneCommandParser parser = new MarkTodoAsDoneCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        EditTodoDescriptor editTodoDescriptor = new EditTodoDescriptor();
        editTodoDescriptor.setStatus(new TodoStatus(true));
        assertParseSuccess(parser, "1", new MarkTodoAsDoneCommand(INDEX_FIRST, editTodoDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_INDEX, "a"));
    }

}
