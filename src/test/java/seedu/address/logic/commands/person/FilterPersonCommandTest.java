package seedu.address.logic.commands.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.person.FilterCriteria;
import seedu.address.model.person.PersonColumn;
import seedu.address.model.person.PersonManagerWithFilteredList;
import seedu.address.model.person.PersonPredicate;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterPersonCommand}.
 */
public class FilterPersonCommandTest {
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
    public void equals() {
        Map<PersonColumn, FilterCriteria> firstPredicateMap = new HashMap<>();
        Map<PersonColumn, FilterCriteria> secondPredicateMap = new HashMap<>();
        firstPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND, Arrays.asList(
                "first", "test")));
        secondPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList(
                "second", "test")));

        PersonPredicate firstPredicate = new PersonPredicate(firstPredicateMap);
        PersonPredicate secondPredicate = new PersonPredicate(secondPredicateMap);

        FilterPersonCommand findFirstCommand = new FilterPersonCommand(firstPredicate);
        FilterPersonCommand findSecondCommand = new FilterPersonCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FilterPersonCommand findFirstCommandCopy = new FilterPersonCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different predicates -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_nameFilter_multiplePersonsFound() {
        Map<PersonColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList("Carl",
                "Daniel")));

        PersonPredicate predicate = new PersonPredicate(predicateMap);
        FilterPersonCommand command = new FilterPersonCommand(predicate);

        expectedModel.getPersonManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL),
                model.getPersonManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_multipleFilters_matchingPersonFound() {
        Map<PersonColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND, List.of("alice")));
        predicateMap.put(PersonColumn.TAG, new FilterCriteria(Operator.AND, List.of("friends")));

        PersonPredicate predicate = new PersonPredicate(predicateMap);
        FilterPersonCommand command = new FilterPersonCommand(predicate);

        expectedModel.getPersonManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE),
                model.getPersonManagerAndList().getFilteredItemsList());
    }
}
