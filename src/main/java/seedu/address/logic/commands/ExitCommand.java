package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD_EXIT = "exit";
    public static final String COMMAND_WORD_BYE = "bye";
    public static final String COMMAND_WORD_QUIT = "quit";
    public static final String COMMAND_WORD_KILL = "kill";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";
    public static final String MESSAGE_USAGE = COMMAND_WORD_EXIT + ", " + COMMAND_WORD_BYE + ", " + COMMAND_WORD_QUIT
        + ", " + COMMAND_WORD_KILL + ": Exits the program.\n";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
