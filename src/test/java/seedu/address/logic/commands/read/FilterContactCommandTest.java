package seedu.address.logic.commands.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_SEARCH_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.CARL;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.ELLE;
import static seedu.address.testutil.TypicalContacts.FIONA;
import static seedu.address.testutil.TypicalContacts.GEORGE;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.model.ColumnPredicate;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactColumn;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.contact.ContactPredicate;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterContactCommand}.
 */
public class FilterContactCommandTest {
    private final Model model = new ModelManager(new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()), new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList());
    private final Model expectedModel = new ModelManager(new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()), new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList());

    @Test
    public void equals() {
        Map<ContactColumn, ColumnPredicate> firstPredicateMap = new HashMap<>();
        Map<ContactColumn, ColumnPredicate> secondPredicateMap = new HashMap<>();
        firstPredicateMap.put(ContactColumn.NAME, new ColumnPredicate(Operator.AND, Arrays.asList("first", "test")));
        secondPredicateMap.put(ContactColumn.NAME, new ColumnPredicate(Operator.OR, Arrays.asList("second", "test")));

        ContactPredicate firstPredicate = new ContactPredicate(firstPredicateMap);
        ContactPredicate secondPredicate = new ContactPredicate(secondPredicateMap);

        FilterContactCommand findFirstCommand = new FilterContactCommand(firstPredicate);
        FilterContactCommand findSecondCommand = new FilterContactCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FilterContactCommand findFirstCommandCopy = new FilterContactCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different predicates -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_idFilterOr() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.ID,
                new ColumnPredicate(Operator.OR, Arrays.asList("A00000001", "A00000003")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_idFilterAnd_noContactsFound() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.ID,
                new ColumnPredicate(Operator.AND, Arrays.asList("A00000001", "A00000003")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 0);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_idFilterNand_excludesMatchingContacts() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.ID,
                new ColumnPredicate(Operator.NAND, Arrays.asList("A00000001", "A00000003")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 7);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_idFilterNor_contactsRemaining() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.ID,
                new ColumnPredicate(Operator.NOR, Arrays.asList("A00000001", "A00000003")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 5);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_idpartialMatch() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.ID,
                new ColumnPredicate(Operator.OR, List.of("A0")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 7);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterAnd() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.NAME,
                new ColumnPredicate(Operator.AND, List.of("Alice", "Pauline")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterOr() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.NAME,
                new ColumnPredicate(Operator.OR, List.of("Carl", "Daniel")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(CARL, DANIEL), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterNand() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.NAME,
                new ColumnPredicate(Operator.NAND, List.of("Alice")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 6);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterNor() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.NAME,
                new ColumnPredicate(Operator.NOR, List.of("Alice", "Daniel")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 5);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON, CARL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_namePartialMatch() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.NAME,
                new ColumnPredicate(Operator.OR, List.of("el")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(DANIEL, ELLE), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_emailFilterAnd() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.EMAIL,
                new ColumnPredicate(Operator.AND, List.of("alice@example.com")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_emailFilterOr() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.EMAIL,
                new ColumnPredicate(Operator.OR, List.of("heinz@example.com", "anna@example.com")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(CARL, GEORGE), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_emailFilterNand() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.EMAIL,
                new ColumnPredicate(Operator.NAND, List.of("alice@example.com")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 6);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_emailFilterNor() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.EMAIL,
                new ColumnPredicate(Operator.NOR, List.of("alice@example.com", "anna@example.com")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 5);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(BENSON, CARL, DANIEL, ELLE, FIONA),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_emailPartialMatch() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.EMAIL,
                new ColumnPredicate(Operator.OR, List.of("el")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(DANIEL), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_courseFilterAnd() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.COURSE,
                new ColumnPredicate(Operator.AND, Arrays.asList("CS60", "CS80")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 0);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_courseFilterOr() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.COURSE,
                new ColumnPredicate(Operator.OR, Arrays.asList("CS50", "CS60")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 6);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_courseFilterNand() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.COURSE,
                new ColumnPredicate(Operator.NAND, Arrays.asList("CS60", "CS50")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 7);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_courseFilterNor() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.COURSE,
                new ColumnPredicate(Operator.NOR, Arrays.asList("CS60", "CS70")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_courseFilterPartialMatchAnd() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.COURSE,
                new ColumnPredicate(Operator.AND, Arrays.asList("CS", "60")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 4);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, ELLE, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_groupFilterAnd() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.GROUP,
                new ColumnPredicate(Operator.AND, Arrays.asList("T33", "T34")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 0);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_groupFilterOr() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.GROUP,
                new ColumnPredicate(Operator.OR, Arrays.asList("T33", "T34")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 6);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_groupFilterNand() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.GROUP,
                new ColumnPredicate(Operator.NAND, Arrays.asList("T33", "T34")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 7);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_groupFilterNor() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.GROUP,
                new ColumnPredicate(Operator.NOR, Arrays.asList("T33", "T34")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_groupFilterPartialMatch() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.GROUP,
                new ColumnPredicate(Operator.AND, Arrays.asList("33")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 3);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getContactManagerAndList().getFilteredItemsList());
    }


    @Test
    public void execute_tagFilterAnd() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.TAG,
                new ColumnPredicate(Operator.AND, Arrays.asList("friends", "owesMoney")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterOr() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.TAG,
                new ColumnPredicate(Operator.OR, Arrays.asList("friends", "enemies")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 4);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL, ELLE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterNand() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.TAG,
                new ColumnPredicate(Operator.NAND, Arrays.asList("friends", "owesMoney")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 6);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterNor() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.TAG,
                new ColumnPredicate(Operator.NOR, Arrays.asList("friends")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 4);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA, GEORGE), model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterPartialMatch() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(ContactColumn.TAG,
                new ColumnPredicate(Operator.AND, Arrays.asList("end")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 3);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getContactManagerAndList().getFilteredItemsList());
    }

    /* Joining multiple columns is via and */

    public void execute_multipleColumns() {
        Map<ContactColumn, ColumnPredicate> predicateMap = Map.of(
                ContactColumn.ID, new ColumnPredicate(Operator.AND, List.of("A00000001")),
                ContactColumn.NAME, new ColumnPredicate(Operator.AND, List.of("Alice Pauline")),
                ContactColumn.EMAIL, new ColumnPredicate(Operator.AND, List.of("alice@example.com")),
                ContactColumn.COURSE, new ColumnPredicate(Operator.AND, List.of("CS50")),
                ContactColumn.GROUP, new ColumnPredicate(Operator.AND, List.of("T35")),
                ContactColumn.TAG, new ColumnPredicate(Operator.AND, List.of("friends"))
        );

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(ALICE), model.getContactManagerAndList().getFilteredItemsList());
    }

}
