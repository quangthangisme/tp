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
 * Adds a tag to a Contact in contact.
 */
public class AddTagToContactCommand extends EditContactCommand {
    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified contact.\n"
            + "Parameters: INDEX " + PREFIX_CONTACT_TAG_LONG + " <tag>\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_CONTACT_TAG_LONG + " TA";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to contact: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag is already assigned to this contact";

    private final Set<Tag> newTags;

    /**
     * Creates an EditCommand to add a tag to the specified {@code Contact}
     */
    public AddTagToContactCommand(Index index, Set<Tag> tags) {
        super(index, new EditContactDescriptor());
        requireNonNull(tags);
        this.newTags = tags;
    }

    @Override
    public Contact createEditedItem(Model model, Contact contactToEdit) throws CommandException {
        requireNonNull(contactToEdit);
        // Every tag that is intended to be added must not already be in the contact
        Set<Tag> existingTags = contactToEdit.getTags();
        for (Tag tag : newTags) {
            if (existingTags.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
            }
        }

        // Merge tags and update
        Set<Tag> combinedTags = new HashSet<>(existingTags);
        combinedTags.addAll(newTags);
        editContactDescriptor.setTags(combinedTags);

        return super.createEditedItem(model, contactToEdit);
    }

    @Override
    public String getSuccessMessage(Contact editedContact) {
        return String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedContact));
    }
}
