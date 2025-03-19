package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private final String feature;
    private boolean isShowingHelp;

    public HelpCommand(String feature) {
        this.feature = feature;
        this.isShowingHelp = false;
    }

    private void showHelp() {
        this.isShowingHelp = true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        UsageMessageProvider provider = new UsageMessageProvider();
        String usageMessage = provider.getMessageUsage(feature);
        if (usageMessage.equals(SHOWING_HELP_MESSAGE)) {
            showHelp();
        }
        return new CommandResult(usageMessage, isShowingHelp, false);
    }
}
