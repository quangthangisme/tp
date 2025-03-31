package seedu.address.logic.commands.update;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManager;
import seedu.address.model.todo.TodoManagerWithFilteredList;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditContactCommand.
 */
public class AddTagToContactCommandTest {

    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );

    @Test
    public void execute_addTagNotPresent_success() {
        Contact initial = model.getContactManagerAndList().getFilteredItemsList()
                .get(INDEX_FIRST.getZeroBased());

        String toAdd = "newTag";
        AddTagToContactCommand addTagCommand = new AddTagToContactCommand(INDEX_FIRST, Set.of(new Tag(toAdd)));

        String[] editedTags = Stream.concat(initial.getTags().stream().map(t -> t.tagName), Stream.of(toAdd))
                .toArray(String[]::new);
        Contact editedContact = new ContactBuilder(initial).withTags(editedTags).build();
        String expectedMessage = String.format(AddTagToContactCommand.MESSAGE_ADD_TAG_SUCCESS,
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

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTag_failure() {
        Contact firstContact = model.getContactManagerAndList()
                .getFilteredItemsList().get(INDEX_FIRST.getZeroBased());
        Set<Tag> oneDuplicate = Set.of(firstContact.getTags().stream().findFirst().get());
        AddTagToContactCommand addTagCommand = new AddTagToContactCommand(INDEX_FIRST, oneDuplicate);
        assertCommandFailure(addTagCommand, model, AddTagToContactCommand.MESSAGE_DUPLICATE_TAGS);
    }

}
