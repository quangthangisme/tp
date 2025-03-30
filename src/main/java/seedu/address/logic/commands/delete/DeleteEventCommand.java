package seedu.address.logic.commands.delete;

import static seedu.address.logic.EventMessages.MESSAGE_INDEX_OUT_OF_RANGE_EVENT;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Deletes an event identified using its displayed index.
 */
public class DeleteEventCommand extends DeleteCommand<Event> {

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Examples " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted event: %1$s";

    /**
     * Creates a DeleteEventCommand to delete the event at the specified {@code targetIndex}.
     */
    public DeleteEventCommand(Index targetIndex) {
        super(targetIndex, Model::getEventManagerAndList);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return MESSAGE_INDEX_OUT_OF_RANGE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event event) {
        return String.format(MESSAGE_DELETE_EVENT_SUCCESS, Messages.format(event));
    }

}
