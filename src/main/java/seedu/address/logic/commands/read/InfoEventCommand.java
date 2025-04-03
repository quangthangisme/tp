package seedu.address.logic.commands.read;

import static seedu.address.logic.EventMessages.MESSAGE_INDEX_OUT_OF_RANGE_EVENT;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManagerAndList;

/**
 * Displays information of an event identified using its displayed index.
 */
public class InfoEventCommand extends InfoCommand<EventManagerAndList, Event> {

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Displays the complete information belonging to the event"
            + " identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX\n"
            + "INDEX must be a positive integer.\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_INFO_EVENT_SUCCESS = "Displaying full information of event!\n%1$s";

    /**
     * Creates an {@code InfoEventCommand} to display information of the {@code Event} at the specified {@code index}.
     */
    public InfoEventCommand(Index index) {
        super(index, Model::getEventManagerAndList);
    }

    @Override
    public String getIndexOutOfRangeMessage() {
        return MESSAGE_INDEX_OUT_OF_RANGE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event itemToDisplay) {
        return String.format(MESSAGE_INFO_EVENT_SUCCESS, Messages.format(itemToDisplay));
    }

}
