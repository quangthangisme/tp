package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.Event;

/**
 * Adds a tag to a specified event
 */
public class AddTagToEventCommand extends EditEventCommand {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified event.\n"
            + "Parameters: INDEX "
            + PREFIX_EVENT_TAG_LONG + " <tag>\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_TAG_LONG + " TA ";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag to event: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag is already assigned to this event";

    private final Set<Tag> newTags;

    /**
     * Creates a AddTagToEventCommand to add tags to an event at a specific index.
     */
    public AddTagToEventCommand(Index index, Set<Tag> tags) {
        super(index, new EditEventDescriptor());
        requireNonNull(tags);
        this.newTags = tags;
    }

    @Override
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        requireNonNull(eventToEdit);
        // Every tag that is intended to be added must not already be in the event
        Set<Tag> existingTags = eventToEdit.getTags();
        for (Tag tag : newTags) {
            if (existingTags.contains(tag)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
            }
        }

        // Merge tags and update
        Set<Tag> combinedTags = new HashSet<>(existingTags);
        combinedTags.addAll(newTags);
        editEventDescriptor.setTags(combinedTags);

        return super.createEditedItem(model, eventToEdit);
    }

    @Override
    public String getSuccessMessage(Event editedItem) {
        return String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedItem));
    }

}
