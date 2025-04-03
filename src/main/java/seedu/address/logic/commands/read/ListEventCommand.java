package seedu.address.logic.commands.read;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.ui.ListPanelViewType;

/**
 * Lists all events to the user.
 */
public class ListEventCommand extends ListCommand<Event> {

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD + ": Lists all events.";
    public static final String MESSAGE_SUCCESS = "Listed all events";

    /**
     * Creates a {@code ListEventCommand}.
     */
    public ListEventCommand() {
        super(Model::getEventManagerAndList);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }

    @Override
    public ListPanelViewType getListPanelViewType() {
        return ListPanelViewType.EVENT;
    }
}
