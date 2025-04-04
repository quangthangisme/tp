package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.ContactMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManagerAndList;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * Edits the details of an existing contact in the address book.
 */
public class EditContactCommand extends EditCommand<ContactManagerAndList, Contact> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the contact identified by the index number used in the displayed contact list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX "
            + "[" + PREFIX_CONTACT_ID_LONG + "ID] "
            + "[" + PREFIX_CONTACT_NAME_LONG + "NAME] "
            + "[" + PREFIX_CONTACT_EMAIL_LONG + "EMAIL] "
            + "[" + PREFIX_CONTACT_COURSE_LONG + "COURSE] "
            + "[" + PREFIX_CONTACT_GROUP_LONG + "GROUP] "
            + "[" + PREFIX_CONTACT_TAG_LONG + "TAG(S)]\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT_EMAIL_LONG + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";

    protected final EditContactDescriptor editContactDescriptor;

    /**
     * @param index                 of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Index index, EditContactDescriptor editContactDescriptor) {
        super(index, Model::getContactManagerAndList);
        requireNonNull(editContactDescriptor);
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    @Override
    public void cascade(Model model, Contact itemToEdit, Contact editedItem) {
        model.getEventManagerAndList().findAndReplace(itemToEdit, editedItem);
        model.getTodoManagerAndList().findAndReplace(itemToEdit, editedItem);
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit} edited with
     * {@code editContactDescriptor}.
     */
    public Contact createEditedItem(Model model, Contact contactToEdit) throws CommandException {
        assert contactToEdit != null;

        Id updatedId = editContactDescriptor.getId().orElse(contactToEdit.getId());
        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        Course updatedCourse = editContactDescriptor.getCourse().orElse(contactToEdit.getCourse());
        Group updatedGroup = editContactDescriptor.getGroup().orElse(contactToEdit.getGroup());
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());

        return new Contact(updatedId, updatedName, updatedEmail, updatedCourse,
                updatedGroup, updatedTags);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_CONTACT;
    }

    @Override
    public String getSuccessMessage(Contact editedItem) {
        return String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand otherEditCommand)) {
            return false;
        }

        return targetIndex.equals(otherEditCommand.targetIndex)
                && editContactDescriptor.equals(otherEditCommand.editContactDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editContactDescriptor", editContactDescriptor)
                .toString();
    }
}
