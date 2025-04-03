package seedu.address.logic.commands.delete;

import static seedu.address.logic.ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManagerAndList;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class DeleteContactCommand extends DeleteCommand<ContactManagerAndList, Contact> {

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    /**
     * Creates a {@code DeleteContactCommand} to delete the contact at the specified {@code targetIndex}
     */
    public DeleteContactCommand(Index targetIndex) {
        super(targetIndex, Model::getContactManagerAndList);
    }

    @Override
    public void cascade(Model model, Contact itemToDelete) {
        model.getTodoManagerAndList().findAndRemove(itemToDelete);
        model.getEventManagerAndList().findAndRemove(itemToDelete);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
    }

    @Override
    public String getSuccessMessage(Contact contactToDelete) {
        return String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete));
    }

}
