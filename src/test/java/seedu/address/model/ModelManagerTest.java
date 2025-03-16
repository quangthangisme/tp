package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonManager;
import seedu.address.model.person.PersonManagerWithFilteredList;
import seedu.address.model.todo.TodoMangerWithFilteredList;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PersonManager(),
                new PersonManager(modelManager.getPersonManagerAndList().getItemManager()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> modelManager.getPersonManagerAndList().hasItem(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.getPersonManagerAndList().hasItem(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.getPersonManagerAndList().addItem(ALICE);
        assertTrue(modelManager.getPersonManagerAndList().hasItem(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getPersonManagerAndList().getFilteredItemsList().remove(0));
    }

    @Test
    public void equals() {
        PersonManager personManager = new AddressBookBuilder().withPerson(ALICE)
                .withPerson(BENSON).build();
        PersonManager differentPersonManager = new PersonManager();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(
                userPrefs,
                new PersonManagerWithFilteredList(personManager),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        ModelManager modelManagerCopy = new ModelManager(
                userPrefs,
                new PersonManagerWithFilteredList(personManager),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                userPrefs,
                new PersonManagerWithFilteredList(differentPersonManager),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        )));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.getPersonManagerAndList()
                .updateFilteredItemsList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(
                userPrefs,
                new PersonManagerWithFilteredList(personManager),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        )));

        // resets modelManager to initial state for upcoming tests
        modelManager.getPersonManagerAndList().updateFilteredItemsList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(
                differentUserPrefs,
                new PersonManagerWithFilteredList(personManager),
                new TodoMangerWithFilteredList(),
                new EventManagerWithFilteredList()
        )));
    }
}
