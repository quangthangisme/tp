package seedu.address.logic.commands.read;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TodoCommandTestUtil.showTodoAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalTodos.getTypicalTodoList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTodoCommand.
 */
public class ListTodoCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                new UserPrefs(),
                // Contacts can be initialized as empty or with typical data if needed.
                new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(getTypicalTodoList()),
                new EventManagerWithFilteredList()
        );
        expectedModel = new ModelManager(
                new UserPrefs(),
                model.getContactManagerAndList(),
                model.getTodoManagerAndList(),
                model.getEventManagerAndList()
        );
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListTodoCommand(), model, ListTodoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTodoAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListTodoCommand(), model, ListTodoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
