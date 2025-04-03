package seedu.address.logic.commands.delete;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;

import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.EventManagerAndList;

/**
 * Clears the event list.
 */
public class ClearEventCommand extends ClearCommand<EventManagerAndList, Event> {

    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Clears the event list.\n";
    public static final String MESSAGE_SUCCESS = "Event list has been cleared!";

    /**
     * Creates a {@code ClearEventCommand} to clear the event list in the Model.
     */
    public ClearEventCommand() {
        super(Model::getEventManagerAndList);
    }

    @Override
    public void clear(EventManagerAndList managerAndList) {
        managerAndList.setItemManager(new EventManager());
    }

    @Override
    public void cascade(Model model) {
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
