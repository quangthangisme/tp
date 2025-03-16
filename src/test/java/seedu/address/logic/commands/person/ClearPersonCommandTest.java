package seedu.address.logic.commands.person;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.person.PersonManager;
import seedu.address.model.person.PersonManagerWithFilteredList;
import seedu.address.model.todo.TodoMangerWithFilteredList;

public class ClearPersonCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearPersonCommand(), model, ClearPersonCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(
                new UserPrefs(),
                new PersonManagerWithFilteredList(getTypicalAddressBook()),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new PersonManagerWithFilteredList(getTypicalAddressBook()),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        expectedModel.getPersonManagerAndList().setItemManager(new PersonManager());

        assertCommandSuccess(new ClearPersonCommand(), model, ClearPersonCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
