package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonManager;
import seedu.address.model.todo.Todo;
import seedu.address.testutil.PersonBuilder;

public class AddPersonCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPersonCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        PersonManagerAndListStubAcceptingPersonAdded managerAndListStub =
                new PersonManagerAndListStubAcceptingPersonAdded();
        ModelStub modelStub = new ModelStub(managerAndListStub);
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddPersonCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), managerAndListStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddPersonCommand addPersonCommand = new AddPersonCommand(validPerson);
        ModelStub modelStub = new ModelStub(new PersonManagerAndListStub(validPerson));

        assertThrows(CommandException.class, AddPersonCommand.MESSAGE_DUPLICATE_PERSON, () ->
                addPersonCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddPersonCommand addAliceCommand = new AddPersonCommand(alice);
        AddPersonCommand addBobCommand = new AddPersonCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPersonCommand addAliceCommandCopy = new AddPersonCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddPersonCommand addPersonCommand = new AddPersonCommand(ALICE);
        String expected = AddPersonCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addPersonCommand.toString());
    }

    /**
     * A default model stub.
     */
    private class ModelStub implements Model {
        private final ItemManagerWithFilteredList<Person> managerAndList;

        private ModelStub(ItemManagerWithFilteredList<Person> managerAndList) {
            this.managerAndList = managerAndList;
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getTodoListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTodoListFilePath(Path todoListFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getEventListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventListFilePath(Path eventListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ItemManagerWithFilteredList<Person> getPersonManagerAndList() {
            return managerAndList;
        }

        @Override
        public ItemManagerWithFilteredList<Todo> getTodoManagerAndList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ItemManagerWithFilteredList<Event> getEventManagerAndList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A ItemManagerWithFilteredList stub that contains a single person.
     */
    private class PersonManagerAndListStub extends ItemManagerWithFilteredList<Person> {
        private final Person person;

        PersonManagerAndListStub(Person person) {
            super(new PersonManager());
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasItem(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A ItemManagerWithFilteredList stub that always accept the person being added.
     */
    private class PersonManagerAndListStubAcceptingPersonAdded
            extends ItemManagerWithFilteredList<Person> {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        public PersonManagerAndListStubAcceptingPersonAdded() {
            super(new PersonManager());
        }

        @Override
        public boolean hasItem(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addItem(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ItemManager<Person> getItemManager() {
            return new PersonManager();
        }
    }

}
