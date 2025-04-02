package seedu.address.logic.commands.delete;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

public class ClearEventCommandTest {

    @Test
    public void execute_emptyEventList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyTodoList_success() {
        Model model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList(getTypicalEventList())
        );
        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList(getTypicalEventList())
        );
        expectedModel.getEventManagerAndList().setItemManager(new EventManager());

        assertCommandSuccess(new ClearEventCommand(), model, ClearEventCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
