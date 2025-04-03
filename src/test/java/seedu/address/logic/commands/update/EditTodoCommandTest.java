package seedu.address.logic.commands.update;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
import static seedu.address.logic.TodoMessages.MESSAGE_DUPLICATE_TODO;
import static seedu.address.logic.TodoMessages.MESSAGE_INDEX_OUT_OF_RANGE_TODO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ContactCommandTestUtil.showContactAtIndex;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_NAME_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.VALID_TAG_REPORT;
import static seedu.address.logic.commands.TodoCommandTestUtil.showTodoAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalTodos.STUFF;
import static seedu.address.testutil.TypicalTodos.STUFF_2;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import seedu.address.testutil.EditTodoDescriptorBuilder;
import seedu.address.testutil.TodoBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTodoCommand.
 */
public class EditTodoCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        model.getTodoManagerAndList().getItemManager().addItem(STUFF);
        model.getTodoManagerAndList().getItemManager().addItem(STUFF_2);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Todo editedTodo = new TodoBuilder().build();
        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder(editedTodo).build();
        EditTodoCommand editCommand = new EditTodoCommand(INDEX_FIRST, descriptor,
                List.of(INDEX_FIRST));

        editedTodo = new TodoBuilder().withContacts(
                model.getContactManagerAndList().getFilteredItemsList().get(0)
        ).build();

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS,
                Messages.format(editedTodo));

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
        expectedModel.getTodoManagerAndList()
                .setItem(model.getTodoManagerAndList().getFilteredItemsList().get(0), editedTodo);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTodo = Index.fromOneBased(model.getTodoManagerAndList()
                .getFilteredItemsList().size());
        Todo lastTodo = model.getTodoManagerAndList()
                .getFilteredItemsList().get(indexLastTodo.getZeroBased());

        TodoBuilder todoInList = new TodoBuilder(lastTodo);
        Todo editedTodo = todoInList
                .withName(VALID_NAME_REPORT)
                .withTags(VALID_TAG_REPORT)
                .build();

        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder()
                .withName(VALID_NAME_REPORT)
                .withTags(VALID_TAG_REPORT)
                .build();
        EditTodoCommand editCommand = new EditTodoCommand(indexLastTodo, descriptor);

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS,
                Messages.format(editedTodo));

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
        expectedModel.getTodoManagerAndList().setItem(lastTodo, editedTodo);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTodoCommand editCommand = new EditTodoCommand(INDEX_FIRST,
                new EditTodoDescriptor());
        Todo editedTodo = model.getTodoManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS,
                Messages.format(editedTodo));

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

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredAndSortedList_success() {
        showTodoAtIndex(model, INDEX_FIRST);

        Todo todoInFilteredList = model.getTodoManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());
        Todo editedTodo = new TodoBuilder(todoInFilteredList).withName(VALID_NAME_REPORT).build();
        EditTodoCommand editCommand = new EditTodoCommand(INDEX_FIRST,
                new EditTodoDescriptorBuilder().withName(VALID_NAME_REPORT).build());

        String expectedMessage = String.format(EditTodoCommand.MESSAGE_EDIT_TODO_SUCCESS,
                Messages.format(editedTodo));

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
        expectedModel.getTodoManagerAndList().setItem(model.getTodoManagerAndList()
                .getFilteredItemsList().get(0), editedTodo);
        showTodoAtIndex(expectedModel, INDEX_FIRST);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTodoUnfilteredList_failure() {
        Todo firstTodo = model.getTodoManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder(firstTodo).build();
        EditTodoCommand editCommand = new EditTodoCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_TODO);
    }

    @Test
    public void execute_duplicateTodoFilteredList_failure() {
        showTodoAtIndex(model, INDEX_FIRST);

        Todo todoInList = model.getTodoManagerAndList().getItemManager().getItemList()
                .get(INDEX_SECOND.getZeroBased());
        EditTodoCommand editCommand = new EditTodoCommand(INDEX_FIRST,
                new EditTodoDescriptorBuilder(todoInList).build());

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_TODO);
    }

    @Test
    public void execute_invalidTodoIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getTodoManagerAndList()
                .getFilteredItemsList().size() + 1);
        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder()
                .withName(VALID_NAME_REPORT).build();
        EditTodoCommand editCommand = new EditTodoCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_TODO, outOfBoundIndex.getOneBased()));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of
     * address book
     */
    @Test
    public void execute_invalidTodoIndexFilteredList_failure() {
        showTodoAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTodoManagerAndList().getItemManager()
                .getItemList().size());

        EditTodoCommand editCommand = new EditTodoCommand(outOfBoundIndex,
                new EditTodoDescriptorBuilder().withName(VALID_NAME_REPORT).build());

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_TODO, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getContactManagerAndList()
                .getFilteredItemsList().size() + 1);
        EditTodoDescriptor descriptor = new EditTodoDescriptorBuilder()
                .withName(VALID_NAME_REPORT).build();
        EditTodoCommand editCommand = new EditTodoCommand(INDEX_FIRST, descriptor,
                List.of(outOfBoundIndex));

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_CONTACT, outOfBoundIndex.getOneBased()));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of
     * address book
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getContactManagerAndList().getItemManager()
                .getItemList().size());

        EditTodoCommand editCommand = new EditTodoCommand(INDEX_FIRST,
                new EditTodoDescriptorBuilder().withName(VALID_NAME_REPORT).build(),
                List.of(outOfBoundIndex));

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_CONTACT, outOfBoundIndex.getOneBased()));
    }
}
