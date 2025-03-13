package seedu.address.logic.commands.event;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_START_DATETIME;

import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an event.
 */
public class AddEventCommand extends AddCommand<Event> {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = EVENT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a event to the app. "
            + "Parameters: "
            + PREFIX_EVENT_NAME + "NAME "
            + PREFIX_START_DATETIME + "PREFIX_START_DEADLINE "
            + PREFIX_EVENT_LOCATION + "LOCATION\n"
            + "Example: " + EVENT_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_EVENT_NAME + "CS2040S tutorial "
            + PREFIX_START_DATETIME + "24-08-26 12:00 "
            + PREFIX_END_DATETIME + "24-08-26 14:00 "
            + PREFIX_EVENT_LOCATION + "NUS SoC COM1";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists";

    public AddEventCommand(Event event) {
        super(event, Model::getEventManagerAndList);
    }

    @Override
    public String getDuplicateItemMessage() {
        return MESSAGE_DUPLICATE_EVENT;
    }

    @Override
    public String getSuccessMessage(Event event) {
        return String.format(MESSAGE_SUCCESS, Messages.format(event));
    }
}
