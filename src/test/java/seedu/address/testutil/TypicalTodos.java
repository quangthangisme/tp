package seedu.address.testutil;

import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_DEADLINE_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_DEADLINE_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_LOCATION_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_LOCATION_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_TAG_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_TAG_REPORT_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.todo.Todo;

/**
 * A utility class containing a list of {@code Todo} objects to be used in tests.
 */
public class TypicalTodos {
    public static final Todo STUFF = new TodoBuilder()
            .withName("stuff")
            .withDeadline("28-02-29 23:29")
            .withLocation("NUS").build();

    public static final Todo STUFF_2 = new TodoBuilder()
            .withName("stuff2")
            .withDeadline("28-02-29 23:29")
            .withLocation("NUS").build();

    public static final Todo GRADING = new TodoBuilder()
            .withName(VALID_NAME_GRADING)
            .withDeadline(VALID_DEADLINE_GRADING)
            .withLocation(VALID_LOCATION_GRADING).build();

    public static final Todo REPORT_WITH_TAG = new TodoBuilder()
            .withName(VALID_NAME_REPORT)
            .withDeadline(VALID_DEADLINE_REPORT)
            .withLocation(VALID_LOCATION_REPORT)
            .withTags(VALID_TAG_REPORT).build();

    public static final Todo REPORT_WITH_MULTIPLE_TAGS = new TodoBuilder()
            .withName(VALID_NAME_REPORT)
            .withDeadline(VALID_DEADLINE_REPORT)
            .withLocation(VALID_LOCATION_REPORT)
            .withTags(VALID_TAG_REPORT_2, VALID_TAG_REPORT).build();

    public static final Todo REPORT = new TodoBuilder()
            .withName(VALID_NAME_REPORT)
            .withDeadline(VALID_DEADLINE_REPORT)
            .withLocation(VALID_LOCATION_REPORT)
            .withTags(VALID_TAG_REPORT).build();

    private TypicalTodos() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical todos.
     */
    public static List<Todo> getTypicalTodos() {
        return new ArrayList<>(
                Arrays.asList(STUFF, STUFF_2, GRADING, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS, REPORT));
    }
}
