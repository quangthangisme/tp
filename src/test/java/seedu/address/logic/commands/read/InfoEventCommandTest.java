package seedu.address.logic.commands.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EventCommandTestUtil.showEventAtIndex;
import static seedu.address.logic.commands.read.InfoEventCommand.MESSAGE_INFO_EVENT_SUCCESS;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;


public class InfoEventCommandTest {

    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList(getTypicalEventList())
    );
    private final Model expectedModel = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList(getTypicalEventList())
    );

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToShow =
                model.getEventManagerAndList().getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        InfoEventCommand infoCommand = new InfoEventCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_INFO_EVENT_SUCCESS,
                Messages.format(eventToShow));
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getEventManagerAndList()
                .getFilteredItemsList().size() + 1);
        InfoEventCommand infoCommand = new InfoEventCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model,
                String.format(EventMessages.MESSAGE_INDEX_OUT_OF_RANGE_EVENT,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST);
        showEventAtIndex(expectedModel, INDEX_FIRST);
        Event eventToShow = model.getEventManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        InfoEventCommand infoCommand = new InfoEventCommand(INDEX_FIRST);
        String expectedMessage = String.format(MESSAGE_INFO_EVENT_SUCCESS,
                Messages.format(eventToShow));
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventManagerAndList().getItemManager()
                .getItemList().size());
        InfoEventCommand infoCommand = new InfoEventCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model,
                String.format(EventMessages.MESSAGE_INDEX_OUT_OF_RANGE_EVENT,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void equals() {
        InfoEventCommand showFirstCommand = new InfoEventCommand(INDEX_FIRST);
        InfoEventCommand showSecondCommand = new InfoEventCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        InfoEventCommand showFirstCommandCopy = new InfoEventCommand(INDEX_FIRST);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        InfoEventCommand infoCommand = new InfoEventCommand(INDEX_FIRST);
        String expected =
                InfoEventCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST + "}";
        assertEquals(expected, infoCommand.toString());
    }
}
