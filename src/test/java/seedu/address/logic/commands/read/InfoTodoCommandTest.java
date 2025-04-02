package seedu.address.logic.commands.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TodoCommandTestUtil.showTodoAtIndex;
import static seedu.address.logic.commands.read.InfoTodoCommand.MESSAGE_INFO_TODO_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTodos.getTypicalTodoList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code InfoContactCommand}.
 */
public class InfoTodoCommandTest {
    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(getTypicalTodoList()),
            new EventManagerWithFilteredList()
    );
    private final Model expectedModel = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(getTypicalTodoList()),
            new EventManagerWithFilteredList()
    );

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Todo todoToShow =
                model.getTodoManagerAndList().getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        InfoTodoCommand infoCommand = new InfoTodoCommand(INDEX_FIRST);

        String expectedMessage = String.format(MESSAGE_INFO_TODO_SUCCESS,
                Messages.format(todoToShow));
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTodoManagerAndList()
                .getFilteredItemsList().size() + 1);
        InfoTodoCommand infoCommand = new InfoTodoCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model, TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTodoAtIndex(model, INDEX_FIRST);
        showTodoAtIndex(expectedModel, INDEX_FIRST);
        Todo todoToShow = model.getTodoManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        InfoTodoCommand infoCommand = new InfoTodoCommand(INDEX_FIRST);
        String expectedMessage = String.format(MESSAGE_INFO_TODO_SUCCESS,
                Messages.format(todoToShow));
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTodoAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTodoManagerAndList().getItemManager()
                .getItemList().size());
        InfoTodoCommand infoCommand = new InfoTodoCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model, TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO);
    }

    @Test
    public void equals() {
        InfoTodoCommand showFirstCommand = new InfoTodoCommand(INDEX_FIRST);
        InfoTodoCommand showSecondCommand = new InfoTodoCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        InfoTodoCommand showFirstCommandCopy = new InfoTodoCommand(INDEX_FIRST);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different todo -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        InfoTodoCommand infoCommand = new InfoTodoCommand(INDEX_FIRST);
        String expected =
                InfoTodoCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST + "}";
        assertEquals(expected, infoCommand.toString());
    }
}
