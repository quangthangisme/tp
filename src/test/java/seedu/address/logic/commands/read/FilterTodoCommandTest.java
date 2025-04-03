package seedu.address.logic.commands.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_SEARCH_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManagerWithFilteredList;
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
}
