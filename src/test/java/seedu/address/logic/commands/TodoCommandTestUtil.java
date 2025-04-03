package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.todo.Todo;

/**
 * Contains helper methods for testing todo commands.
 */
public class TodoCommandTestUtil {

    public static final String VALID_NAME_REPORT = "Report";
    public static final String VALID_NAME_REPORT_TAG = "Report tag";
    public static final String VALID_NAME_REPORT_MULTIPLE_TAGS = "Report with multiple tags";
    public static final String VALID_NAME_GRADING = "Grading submissions";
    public static final String VALID_DEADLINE_REPORT = "25-12-31 23:59";
    public static final String VALID_DEADLINE_GRADING = "25-11-30 23:59";
    public static final String VALID_LOCATION_REPORT = "Canvas";
    public static final String VALID_LOCATION_GRADING = "Prof office";
    public static final String VALID_TAG_REPORT = "important";
    public static final String VALID_TAG_REPORT_2 = "better-than-you";
    public static final String VALID_STATUS = "TrUe";

    public static final String NAME_DESC_REPORT = " " + PREFIX_TODO_NAME_LONG + VALID_NAME_REPORT;
    public static final String NAME_DESC_GRADING = " " + PREFIX_TODO_NAME_LONG + VALID_NAME_GRADING;
    public static final String DEADLINE_DESC_REPORT =
            " " + PREFIX_TODO_DEADLINE_LONG + VALID_DEADLINE_REPORT;
    public static final String DEADLINE_DESC_GRADING =
            " " + PREFIX_TODO_DEADLINE_LONG + VALID_DEADLINE_GRADING;
    public static final String LOCATION_DESC_REPORT =
            " " + PREFIX_TODO_LOCATION_LONG + VALID_LOCATION_REPORT;
    public static final String LOCATION_DESC_GRADING =
            " " + PREFIX_TODO_LOCATION_LONG + VALID_LOCATION_GRADING;
    public static final String TAG_DESC_REPORT = " " + PREFIX_TODO_TAG_LONG + VALID_TAG_REPORT;
    public static final String TAG_DESC_REPORT_MULTIPLE =
            " " + PREFIX_TODO_TAG_LONG + VALID_TAG_REPORT + " " + VALID_TAG_REPORT_2;
    public static final String STATUS_DESC = " " + PREFIX_TODO_STATUS_LONG + VALID_STATUS;
    public static final String LINKED_CONTACTS_DESC =
            " " + PREFIX_TODO_LINKED_CONTACT_LONG + "1 2";

    public static final String INVALID_TODO_NAME_DESC = " " + PREFIX_TODO_NAME_LONG + "-stuff";
    public static final String INVALID_TODO_DEADLINE_DESC_NOT_DATETIME =
            " " + PREFIX_TODO_DEADLINE_LONG + "aaaa";
    public static final String INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT =
            " " + PREFIX_TODO_DEADLINE_LONG + "23:59 25-12-31";
    public static final String INVALID_TODO_LOCATION_DESC =
            " " + PREFIX_TODO_LOCATION_LONG + "NUS -UTR";
    public static final String INVALID_TODO_TAG_DESC = " " + PREFIX_TODO_TAG_LONG + "-too";
    public static final String INVALID_TODO_LINKED_CONTACT_INDEX = "yay";
    public static final String INVALID_TODO_LINKED_CONTACT_DESC =
            " " + PREFIX_TODO_LINKED_CONTACT_LONG + INVALID_TODO_LINKED_CONTACT_INDEX;
    public static final String INVALID_TODO_STATUS = "lolxd";
    public static final String INVALID_TODO_STATUS_DESC =
            " " + PREFIX_TODO_STATUS_LONG + INVALID_TODO_STATUS;



    /**
     * Updates {@code model}'s filtered list to show only the todo at the given {@code targetIndex} in the
     * {@code model}'s todo list.
     */
    public static void showTodoAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased()
                < model.getTodoManagerAndList().getFilteredItemsList().size());

        Todo todo = model.getTodoManagerAndList().getFilteredItemsList()
                .get(targetIndex.getZeroBased());
        model.getTodoManagerAndList().updateFilteredItemsList(todo::equals);
        assertEquals(1, model.getTodoManagerAndList().getFilteredItemsList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the todo.
     */
    public static void showTodo(Model model, Todo todo) {
        model.getTodoManagerAndList().updateFilteredItemsList(todo::equals);
        assertEquals(1, model.getTodoManagerAndList().getFilteredItemsList().size());
    }
}
