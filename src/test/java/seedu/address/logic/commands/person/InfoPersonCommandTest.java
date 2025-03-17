package seedu.address.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_DISPLAY_SPECIFIC_PERSON_INFO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code InfoPersonCommand}.
 */
public class InfoPersonCommandTest {
    private final Model model = new ModelManager(
            new UserPrefs(),
            new PersonManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );
    private final Model expectedModel = new ModelManager(
            new UserPrefs(),
            new PersonManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToShow =
                model.getPersonManagerAndList().getFilteredItemsList().get(INDEX_FIRST_PERSON.getZeroBased());
        InfoPersonCommand infoCommand = new InfoPersonCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MESSAGE_DISPLAY_SPECIFIC_PERSON_INFO,
                personToShow.getName());
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPersonManagerAndList()
                .getFilteredItemsList().size() + 1);
        InfoPersonCommand infoCommand = new InfoPersonCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Person personToShow = model.getPersonManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST_PERSON.getZeroBased());
        InfoPersonCommand infoCommand = new InfoPersonCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(MESSAGE_DISPLAY_SPECIFIC_PERSON_INFO,
                personToShow.getName());
        assertCommandSuccess(infoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPersonManagerAndList().getItemManager()
                .getItemList().size());
        InfoPersonCommand infoCommand = new InfoPersonCommand(outOfBoundIndex);
        assertCommandFailure(infoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        InfoPersonCommand showFirstCommand = new InfoPersonCommand(INDEX_FIRST_PERSON);
        InfoPersonCommand showSecondCommand = new InfoPersonCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        InfoPersonCommand showFirstCommandCopy = new InfoPersonCommand(INDEX_FIRST_PERSON);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));
    }

    @Test
    public void toStringMethod() {
        InfoPersonCommand infoCommand = new InfoPersonCommand(INDEX_FIRST_PERSON);
        String expected =
                InfoPersonCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, infoCommand.toString());
    }
}
