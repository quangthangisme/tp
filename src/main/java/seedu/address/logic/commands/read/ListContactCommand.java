package seedu.address.logic.commands.read;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManagerAndList;
import seedu.address.ui.ListPanelViewType;

/**
 * Lists all contacts to the user.
 */
public class ListContactCommand extends ListCommand<ContactManagerAndList, Contact> {

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Lists all contacts.";
    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    /**
     * Creates a {@code ListContactCommand}.
     */
    public ListContactCommand() {
        super(Model::getContactManagerAndList);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    @Override
    public ListPanelViewType getListPanelViewType() {
        return ListPanelViewType.CONTACT;
    }
}
