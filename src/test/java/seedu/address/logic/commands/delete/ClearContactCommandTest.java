package seedu.address.logic.commands.delete;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TodoBuilder;

public class ClearContactCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        expectedModel.getContactManagerAndList().setItemManager(new ContactManager());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_linkedEventAndTodo_success() {
        Model model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        Contact contact = model.getContactManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());
        model.getEventManagerAndList().addItem(new EventBuilder().withAttendance(contact).build());
        model.getTodoManagerAndList().addItem(new TodoBuilder().withContacts(contact).build());

        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        expectedModel.getContactManagerAndList().setItemManager(new ContactManager());
        expectedModel.getEventManagerAndList().addItem(new EventBuilder().build());
        expectedModel.getTodoManagerAndList().addItem(new TodoBuilder().build());

        assertCommandSuccess(new ClearContactCommand(), model, ClearContactCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
