package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.contact.ContactManagerAndList;

/**
 * Clears the contact list.
 */
public class ClearContactCommand extends ClearCommand<ContactManagerAndList, Contact> {

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Clears the contact list\n";
    public static final String MESSAGE_SUCCESS = "Contact list has been cleared!";

    /**
     * Creates a {@code ClearContactCommand} to clear the contact list in the model.
     */
    public ClearContactCommand() {
        super(Model::getContactManagerAndList);
    }


    @Override
    public void cascade(Model model) {
        model.getTodoManagerAndList().removeAllContacts();
        model.getEventManagerAndList().removeAllContacts();
    }

    @Override
    public void clear(ContactManagerAndList managerAndList) {
        managerAndList.setItemManager(new ContactManager());
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
