package seedu.address.logic.commands.delete;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTodos.getTypicalTodoList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerWithFilteredList;


public class ClearTodoCommandTest {

    @Test
    public void execute_emptyTodoList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearTodoCommand(), model, ClearTodoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyTodoList_success() {
        Model model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(getTypicalTodoList()),
                new EventManagerWithFilteredList()
        );
        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(getTypicalTodoList()),
                new EventManagerWithFilteredList()
        );
        expectedModel.getTodoManagerAndList().setItemManager(new TodoManager());

        assertCommandSuccess(new ClearTodoCommand(), model, ClearTodoCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
