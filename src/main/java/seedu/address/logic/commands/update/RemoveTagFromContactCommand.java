package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.commons.Tag;

/**
 * Removes a tag from a Contact in contact.
 */
public class RemoveTagFromContactCommand extends EditContactCommand {
    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes a tag from a specified contact.\n"
            + "Parameters: INDEX " + PREFIX_CONTACT_TAG_LONG + " TAG(S)\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT_TAG_LONG + " TA driver";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag from contact: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "The tag is not assigned to this contact";

    private final Set<Tag> newTags;

    /**
     * Creates an EditCommand to remove a tag from the specified {@code Contact}
     */
    public RemoveTagFromContactCommand(Index index, Set<Tag> newTags) {
        super(index, new EditContactDescriptor());
        requireNonNull(newTags);
        this.newTags = newTags;
    }

    @Override
    public Contact createEditedItem(Model model, Contact contactToEdit) throws CommandException {
        requireNonNull(contactToEdit);
        // Every tag that is intended to be removed must already be in the contact
        Set<Tag> existingTags = contactToEdit.getTags();
        for (Tag tag : newTags) {
            if (!existingTags.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND));
            }
        }

        // Merge tags and update
        Set<Tag> combinedTags = new HashSet<>(existingTags);
        combinedTags.removeAll(newTags);
        editContactDescriptor.setTags(combinedTags);

        return super.createEditedItem(model, contactToEdit);
    }

    @Override
    public String getSuccessMessage(Contact editedContact) {
        return String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(editedContact));
    }
}
