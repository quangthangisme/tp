package seedu.address.logic.commands.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_SEARCH_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_DEADLINE_GRADING;
import static seedu.address.testutil.TypicalTodos.GRADING;
import static seedu.address.testutil.TypicalTodos.REPORT;
import static seedu.address.testutil.TypicalTodos.REPORT_WITH_MULTIPLE_TAGS;
import static seedu.address.testutil.TypicalTodos.REPORT_WITH_TAG;
import static seedu.address.testutil.TypicalTodos.STUFF;
import static seedu.address.testutil.TypicalTodos.STUFF_2;
import static seedu.address.testutil.TypicalTodos.getTypicalTodoList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.item.predicate.DatetimePredicate;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.item.predicate.TagPredicate;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManagerWithFilteredList;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterTodoCommand}.
 */
public class FilterTodoCommandTest {
    private final Model model = new ModelManager(new UserPrefs(), new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(getTypicalTodoList()), new EventManagerWithFilteredList());
    private final Model expectedModel = new ModelManager(new UserPrefs(), new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(getTypicalTodoList()), new EventManagerWithFilteredList());

    @Test
    public void execute_nameFilterAnd() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.AND, Arrays.asList("report", "tag")));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterOr() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.OR, Arrays.asList("report", "tag")));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterNand() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.NAND, Arrays.asList("report", "tag")));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(GRADING, REPORT, STUFF, STUFF_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterNor() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.NOR, Arrays.asList("report", "tag")));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(GRADING, STUFF, STUFF_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterPartialMatch() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.OR, Arrays.asList("rep")));

        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_deadlineAndFilter() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setDeadlinePredicate(new TodoDeadlinePredicate(Operator.AND,
                Arrays.asList(new DatetimePredicate("[" + VALID_DEADLINE_GRADING + "/-]"),
                        new DatetimePredicate("[-/28-02-29 23:28]"))));

        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(GRADING, REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_deadlineOrFilter() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setDeadlinePredicate(new TodoDeadlinePredicate(Operator.OR,
                Arrays.asList(new DatetimePredicate("[28-02-29 23:29/-]"),
                        new DatetimePredicate("[-/28-02-29 23:28]"))));

        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(GRADING, REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS, STUFF, STUFF_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_deadlineNandFilter() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setDeadlinePredicate(new TodoDeadlinePredicate(Operator.NAND,
                Arrays.asList(new DatetimePredicate("[28-02-29 23:29/-]"),
                        new DatetimePredicate("[-/28-02-29 23:28]"))));

        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(GRADING, REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS, STUFF, STUFF_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_deadlineNorFilter() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setDeadlinePredicate(new TodoDeadlinePredicate(Operator.NOR,
                Arrays.asList(new DatetimePredicate("[-/" + VALID_DEADLINE_GRADING + "]"),
                        new DatetimePredicate("[28-02-29 23:29/-]"))));

        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_deadlinePartialMatchFilter() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setDeadlinePredicate(new TodoDeadlinePredicate(Operator.OR,
                Arrays.asList(
                        new DatetimePredicate("[28-02-29 23:29/-]"),
                        new DatetimePredicate("[-/28-02-29 23:28]"))));

        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(GRADING, REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS, STUFF, STUFF_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_locationFilterAnd() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setLocationPredicate(new LocationPredicate(Operator.AND, Arrays.asList("can", "vas")));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_locationFilterOr() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setLocationPredicate(new LocationPredicate(Operator.OR, Arrays.asList("can", "nus")));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT, REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS, STUFF, STUFF_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterAnd() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.AND, Set.of(new Tag("better"), new Tag("important"))));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterOr() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.OR, Set.of(new Tag("better"), new Tag("important"))));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT_WITH_TAG, REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterPartialMatch() {
        TodoPredicate predicate = new TodoPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.OR, Set.of(new Tag("bet"))));
        FilterTodoCommand command = new FilterTodoCommand(predicate, Optional.empty());

        expectedModel.getTodoManagerAndList().updateFilteredItemsList(predicate);
        List<Todo> expectedList = List.of(REPORT_WITH_MULTIPLE_TAGS);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getTodoManagerAndList().getFilteredItemsList());
    }

}
