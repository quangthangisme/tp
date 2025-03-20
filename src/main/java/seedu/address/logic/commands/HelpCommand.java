package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n" + "Example: "
        + COMMAND_WORD;

    private final String helpMessage;

    /**
     * Constructs a new HelpCommand for a specified feature.
     *
     * @param helpMessage the help message to be displayed;
     */
    public HelpCommand(String helpMessage) {
        this.helpMessage = helpMessage;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(helpMessage, false, false);
    }
}
