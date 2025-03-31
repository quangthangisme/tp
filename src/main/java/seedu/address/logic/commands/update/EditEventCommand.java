package seedu.address.logic.commands.update;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.EventMessages;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventName;

/**
 * Edits the details of an existing event in the address book.
 */
public class EditEventCommand extends EditCommand<Event> {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Edits the details of the event identified by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME_LONG + " NAME] "
            + "[" + PREFIX_EVENT_START_LONG + " START_TIME] "
            + "[" + PREFIX_EVENT_END_LONG + " END_TIME] "
            + "[" + PREFIX_EVENT_LOCATION_LONG + " LOCATION] "
            + "[" + PREFIX_EVENT_TAG_LONG + " TAG]...\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME_LONG + " Complete project\n";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    protected final EditEventDescriptor editEventDescriptor;

    /**
     * @param index               of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        super(index, Model::getEventManagerAndList);
        requireNonNull(editEventDescriptor);
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit} edited with
     * {@code editEventDescriptor}.
     */
    public Event createEditedItem(Model model, Event eventToEdit) throws CommandException {
        assert eventToEdit != null;

        EventName updatedName = editEventDescriptor.getName().orElse(eventToEdit.getName());
        EventDateTime updatedStartTime = editEventDescriptor.getStartTime().orElse(eventToEdit.getStartTime());
        EventDateTime updatedEndTime = editEventDescriptor.getEndTime().orElse(eventToEdit.getEndTime());
        EventLocation updatedLocation = editEventDescriptor.getLocation().orElse(eventToEdit.getLocation());
        List<Contact> updatedContacts = editEventDescriptor.getContacts().orElse(eventToEdit.getContacts());
        List<Boolean> updatedMarkedList = editEventDescriptor.getMarkedList().orElse(eventToEdit.getMarkedList());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());

        return new Event(updatedName, updatedStartTime, updatedEndTime, updatedLocation, updatedContacts,
                updatedMarkedList, updatedTags);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return EventMessages.MESSAGE_INDEX_OUT_OF_RANGE_EVENT;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event editedItem) {
        return String.format(MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedItem));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditEventCommand otherEditCommand)) {
            return false;
        }

        return targetIndex.equals(otherEditCommand.targetIndex)
                && editEventDescriptor.equals(otherEditCommand.editEventDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", targetIndex)
                .add("editEventDescriptor", editEventDescriptor)
                .toString();
    }
}
