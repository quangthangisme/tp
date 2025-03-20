package seedu.address.logic.commands.event;

import seedu.address.logic.abstractcommand.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Lists all events to the user.
 */
public class ListEventCommand extends ListCommand<Event> {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all events";
    public static final String MESSAGE_USAGE = "list";

    /**
     * Creates a {@Code ListEventCommand}.
     */
    public ListEventCommand() {
        super(Model::getEventManagerAndList);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
