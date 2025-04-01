package seedu.address.logic.commands.update;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerWithFilteredList;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.EditContactDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditContactCommand.
 */
public class RemoveTagFromContactCommandTest {

    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );

    @Test
    public void execute_removeTagPresent_success() {
        Contact initial = model.getContactManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());

        String toRemove = initial.getTags().stream().findFirst().get().tagName;
        RemoveTagFromContactCommand removeTagCommand = new RemoveTagFromContactCommand(INDEX_FIRST,
                Set.of(new Tag(toRemove)));

        String[] editedTags = initial.getTags().stream()
                .map(tag -> tag.tagName)
                .filter(tag -> !tag.equals(toRemove))
                .toArray(String[]::new);
        Contact editedContact = new ContactBuilder(initial).withTags(editedTags).build();
        String expectedMessage = String.format(RemoveTagFromContactCommand.MESSAGE_REMOVE_TAG_SUCCESS,
                Messages.format(editedContact));

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
        expectedModel.getContactManagerAndList()
                .setItem(model.getContactManagerAndList().getFilteredItemsList().get(0),
                        editedContact);

        assertCommandSuccess(removeTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTagNotPresent_failure() {
        Contact firstContact = model.getContactManagerAndList().getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact)
                .withTags("randomTag")
                .build();
        RemoveTagFromContactCommand removeTagCommand = new RemoveTagFromContactCommand(INDEX_FIRST,
                Set.of(new Tag("randomTag")));

        assertCommandFailure(removeTagCommand, model, RemoveTagFromContactCommand.MESSAGE_TAG_NOT_FOUND);
    }

}
