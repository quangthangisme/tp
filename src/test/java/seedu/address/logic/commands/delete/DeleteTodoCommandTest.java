package seedu.address.logic.commands.delete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TodoCommandTestUtil.showTodoAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTodos.getTypicalTodoList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteContactCommand}.
 */
public class DeleteTodoCommandTest {

    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(getTypicalTodoList()),
            new EventManagerWithFilteredList()
    );

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Todo todoToDelete = model.getTodoManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteTodoCommand deleteCommand = new DeleteTodoCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteTodoCommand.MESSAGE_DELETE_TODO_SUCCESS,
                Messages.format(todoToDelete));

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
        expectedModel.getTodoManagerAndList().deleteItem(todoToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTodoManagerAndList()
                .getFilteredItemsList().size() + 1);
        DeleteTodoCommand deleteCommand = new DeleteTodoCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, String.format(MESSAGE_INDEX_OUT_OF_RANGE_TODO,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showTodoAtIndex(model, INDEX_FIRST);

        Todo todoToDelete = model.getTodoManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());
        DeleteTodoCommand deleteCommand = new DeleteTodoCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteTodoCommand.MESSAGE_DELETE_TODO_SUCCESS,
                Messages.format(todoToDelete));

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
        expectedModel.getTodoManagerAndList().deleteItem(todoToDelete);
        showNoTodo(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTodoAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTodoManagerAndList().getItemManager()
                .getItemList().size());

        DeleteTodoCommand deleteCommand = new DeleteTodoCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, String.format(MESSAGE_INDEX_OUT_OF_RANGE_TODO,
                outOfBoundIndex.getOneBased()));
    }

    @Test
    public void equals() {
        DeleteTodoCommand deleteFirstCommand = new DeleteTodoCommand(INDEX_FIRST);
        DeleteTodoCommand deleteSecondCommand = new DeleteTodoCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTodoCommand deleteFirstCommandCopy = new DeleteTodoCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteTodoCommand deleteCommand = new DeleteTodoCommand(targetIndex);
        String expected = DeleteTodoCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTodo(Model model) {
        model.getTodoManagerAndList().updateFilteredItemsList(p -> false);

        assertTrue(model.getTodoManagerAndList().getFilteredItemsList().isEmpty());
    }
}
