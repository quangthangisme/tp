package seedu.address.logic.parser.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.TodoDeadline;
import seedu.address.model.todo.TodoLocation;
import seedu.address.model.todo.TodoName;

public class TodoParserUtilTest {
    private static final String INVALID_NAME = "Meeting with R@chel Walker";
    private static final String INVALID_DEADLINE_NOT_DATETIME = "example";
    private static final String INVALID_DEADLINE_INCORRECT_FORMAT = "21:20 25-12-31";
    private static final String INVALID_DEADLINE_INVALID_DATETIME = "25-02-30 23:59";

    private static final String VALID_NAME = "Meeting with Rachel Walker";
    private static final String VALID_LOCATION = "NUS Science";
    private static final String VALID_DEADLINE = "25-12-30 23:59";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TodoParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> TodoParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        TodoName expectedName = new TodoName(VALID_NAME);
        assertEquals(expectedName, TodoParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        TodoName expectedName = new TodoName(VALID_NAME);
        assertEquals(expectedName, TodoParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseDeadline_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TodoParserUtil.parseDeadline((String) null));
    }

    @Test
    public void parseDeadline_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, ()
                -> TodoParserUtil.parseDeadline(INVALID_DEADLINE_INCORRECT_FORMAT));

        assertThrows(ParseException.class, ()
                -> TodoParserUtil.parseDeadline(INVALID_DEADLINE_NOT_DATETIME));

        assertThrows(ParseException.class, ()
                -> TodoParserUtil.parseDeadline(INVALID_DEADLINE_INVALID_DATETIME));
    }

    @Test
    public void parseDeadline_validValueWithoutWhitespace_returnsDeadline() throws Exception {
        TodoDeadline expectedDeadline = new TodoDeadline(VALID_DEADLINE);
        assertEquals(expectedDeadline, TodoParserUtil.parseDeadline(VALID_DEADLINE));
    }

    @Test
    public void parseDeadline_validValueWithWhitespace_returnsTrimmedDeadline() throws Exception {
        String deadlineWithWhitespace = WHITESPACE + VALID_DEADLINE + WHITESPACE;
        TodoDeadline expectedDeadline = new TodoDeadline(VALID_DEADLINE);
        assertEquals(expectedDeadline, TodoParserUtil.parseDeadline(deadlineWithWhitespace));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> TodoParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocation_validValueWithoutWhitespace_returnsDeadline() throws Exception {
        TodoLocation expectedLocation = new TodoLocation(VALID_LOCATION);
        assertEquals(expectedLocation, TodoParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithWhitespace_returnsTrimmedLocation() throws Exception {
        String locationWithWhitespace = WHITESPACE + VALID_LOCATION + WHITESPACE;
        TodoLocation expectedLocation = new TodoLocation(VALID_LOCATION);
        assertEquals(expectedLocation, TodoParserUtil.parseLocation(locationWithWhitespace));
    }
}
