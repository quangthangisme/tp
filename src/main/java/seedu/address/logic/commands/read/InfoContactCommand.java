package seedu.address.logic.commands.read;

import static seedu.address.logic.ContactMessages.MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Displays information of a contact identified using its displayed index.
 * <p>
 * This command is only available when the contact list panel is displayed.
 */
public class InfoContactCommand extends InfoCommand<Contact> {

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Displays the complete information belonging to the contact"
            + " identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 2";

    public static final String MESSAGE_INFO_CONTACT_SUCCESS = "Displaying full information of contact!\n%1$s";

    /**
     * Creates a {@code InfoContactCommand} to display information of the {@code Contact} at the specified {@code
     * index}.
     */
    public InfoContactCommand(Index targetIndex) {
        super(targetIndex, Model::getContactManagerAndList);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return MESSAGE_INDEX_OUT_OF_RANGE_CONTACT;
    }

    @Override
    public String getSuccessMessage(Contact contactToDisplay) {
        return String.format(MESSAGE_INFO_CONTACT_SUCCESS, Messages.format(contactToDisplay));
    }

}
