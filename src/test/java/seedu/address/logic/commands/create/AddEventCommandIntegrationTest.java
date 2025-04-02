package seedu.address.logic.commands.create;

import static seedu.address.logic.EventMessages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EventCommandTestUtil.showEvent;
import static seedu.address.testutil.TypicalEvents.CRYING;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerWithFilteredList;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddEventCommand}.
 */
public class AddEventCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        model.getEventManagerAndList().getItemManager().addItem(CRYING);
    }

    @Test
    public void execute_newEvent_success() {
        Event validEvent = new EventBuilder().build();

        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(
                        new ContactManager(model.getContactManagerAndList().getItemManager())
                ),
                new TodoManagerWithFilteredList(
                        new TodoManager(model.getTodoManagerAndList().getItemManager())
                ),
                new EventManagerWithFilteredList(
                        new EventManager(model.getEventManagerAndList().getItemManager())
                )
        );
        expectedModel.getEventManagerAndList().addItem(validEvent);

        assertCommandSuccess(new AddEventCommand(validEvent), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                expectedModel);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Event eventInList = model.getEventManagerAndList().getItemManager().getItemList().get(0);
        assertCommandFailure(new AddEventCommand(eventInList), model,
                MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_filteredList_success() {
        Event validEvent = new EventBuilder().build();
        model.getEventManagerAndList().updateFilteredItemsList(unused -> false);

        Model expectedModel = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(
                        new ContactManager(model.getContactManagerAndList().getItemManager())
                ),
                new TodoManagerWithFilteredList(
                        new TodoManager(model.getTodoManagerAndList().getItemManager())
                ),
                new EventManagerWithFilteredList(
                        new EventManager(model.getEventManagerAndList().getItemManager())
                )
        );
        expectedModel.getEventManagerAndList().addItem(validEvent);
        showEvent(expectedModel, validEvent);
        assertCommandSuccess(new AddEventCommand(validEvent), model,
                String.format(AddEventCommand.MESSAGE_SUCCESS, Messages.format(validEvent)),
                expectedModel);
    }

}
