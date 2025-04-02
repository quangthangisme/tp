package seedu.address.testutil;

import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_DEADLINE_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_LOCATION_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_LOCATION_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_GRADING;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_TAG_REPORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;

/**
 * A utility class containing a list of {@code Todo} objects to be used in tests.
 */
public class TypicalTodos {
    public static final Todo GRADING = new TodoBuilder()
            .withName(VALID_NAME_GRADING)
            .withDeadline(VALID_DEADLINE_GRADING)
            .withLocation(VALID_LOCATION_GRADING).build();
    public static final Todo REPORT = new TodoBuilder()
            .withName(VALID_NAME_REPORT)
            .withDeadline(VALID_DEADLINE_GRADING)
            .withLocation(VALID_LOCATION_REPORT)
            .withTags(VALID_TAG_REPORT).build();

    private TypicalTodos() {} // prevents instantiation

    /**
     * Returns an {@code TodoList} with all the typical todos.
     */
    public static TodoManager getTypicalTodoList() {
        TodoManager tl = new TodoManager();
        for (Todo todo : getTypicalTodos()) {
            tl.addItem(todo);
        }
        return tl;
    }

    public static List<Todo> getTypicalTodos() {
        return new ArrayList<>(Arrays.asList(GRADING, REPORT));
    }
}
