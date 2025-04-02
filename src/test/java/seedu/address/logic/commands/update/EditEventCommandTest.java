package seedu.address.logic.commands.update;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
import static seedu.address.logic.EventMessages.MESSAGE_DUPLICATE_EVENT;
import static seedu.address.logic.EventMessages.MESSAGE_INDEX_OUT_OF_RANGE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ContactCommandTestUtil.showContactAtIndex;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_NAME_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.VALID_TAG_CRYING;
import static seedu.address.logic.commands.EventCommandTestUtil.showEventAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.STUFF_EVENT;
import static seedu.address.testutil.TypicalEvents.STUFF_EVENT_2;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

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
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerWithFilteredList;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditEventCommand.
 */
public class EditEventCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(
                new UserPrefs(),
                new ContactManagerWithFilteredList(getTypicalAddressBook()),
                new TodoManagerWithFilteredList(),
                new EventManagerWithFilteredList()
        );
        model.getEventManagerAndList().getItemManager().addItem(STUFF_EVENT);
        model.getEventManagerAndList().getItemManager().addItem(STUFF_EVENT_2);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST, descriptor,
                List.of(INDEX_FIRST));

        editedEvent = new EventBuilder().withAttendance(
                model.getContactManagerAndList().getFilteredItemsList().get(0)
        ).build();

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

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
        expectedModel.getEventManagerAndList()
                .setItem(model.getEventManagerAndList().getFilteredItemsList().get(0), editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEvent = Index.fromOneBased(model.getEventManagerAndList()
                .getFilteredItemsList().size());
        Event lastEvent = model.getEventManagerAndList()
                .getFilteredItemsList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList
                .withName(VALID_NAME_CRYING)
                .withTags(VALID_TAG_CRYING)
                .build();

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_CRYING)
                .withTags(VALID_TAG_CRYING)
                .build();
        EditEventCommand editCommand = new EditEventCommand(indexLastEvent, descriptor);

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

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
        expectedModel.getEventManagerAndList().setItem(lastEvent, editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST,
                new EditEventDescriptor());
        Event editedEvent = model.getEventManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

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
        showEventAtIndex(model, INDEX_FIRST);

        Event eventInFilteredList = model.getEventManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());
        Event editedEvent =
                new EventBuilder(eventInFilteredList).withName(VALID_NAME_CRYING).build();
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST,
                new EditEventDescriptorBuilder().withName(VALID_NAME_CRYING).build());

        String expectedMessage = String.format(EditEventCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

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
        expectedModel.getEventManagerAndList().setItem(model.getEventManagerAndList()
                .getFilteredItemsList().get(0), editedEvent);
        showEventAtIndex(expectedModel, INDEX_FIRST);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateEventUnfilteredList_failure() {
        Event firstEvent = model.getEventManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(firstEvent).build();
        EditEventCommand editCommand = new EditEventCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_duplicateEventFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST);

        Event eventInList = model.getEventManagerAndList().getItemManager().getItemList()
                .get(INDEX_SECOND.getZeroBased());
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST,
                new EditEventDescriptorBuilder(eventInList).build());

        assertCommandFailure(editCommand, model, MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getEventManagerAndList()
                .getFilteredItemsList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_CRYING).build();
        EditEventCommand editCommand = new EditEventCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_EVENT, outOfBoundIndex.getOneBased()));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of
     * address book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showEventAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getEventManagerAndList().getItemManager()
                .getItemList().size());

        EditEventCommand editCommand = new EditEventCommand(outOfBoundIndex,
                new EditEventDescriptorBuilder().withName(VALID_NAME_CRYING).build());

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_EVENT, outOfBoundIndex.getOneBased()));
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getContactManagerAndList()
                .getFilteredItemsList().size() + 1);
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withName(VALID_NAME_CRYING).build();
        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST, descriptor,
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

        EditEventCommand editCommand = new EditEventCommand(INDEX_FIRST,
                new EditEventDescriptorBuilder().withName(VALID_NAME_CRYING).build(),
                List.of(outOfBoundIndex));

        assertCommandFailure(editCommand, model,
                String.format(MESSAGE_INDEX_OUT_OF_RANGE_CONTACT, outOfBoundIndex.getOneBased()));
    }
}
