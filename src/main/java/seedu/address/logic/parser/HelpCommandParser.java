package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.BYE_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EXIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.KILL_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.QUIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.DisplayEventInformationCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.ClearPersonCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.commands.person.InfoPersonCommand;
import seedu.address.logic.commands.person.ListPersonCommand;
import seedu.address.logic.commands.todo.AddPersonToTodoCommand;
import seedu.address.logic.commands.todo.AddTodoCommand;
import seedu.address.logic.commands.todo.DeleteTodoCommand;
import seedu.address.logic.commands.todo.DisplayTodoInformationCommand;
import seedu.address.logic.commands.todo.ListTodoCommand;
import seedu.address.logic.commands.todo.MarkTodoAsDoneCommand;
import seedu.address.logic.commands.todo.MarkTodoAsNotDoneCommand;
import seedu.address.logic.commands.todo.RemovePersonFromTodoCommand;

/**
 * Parses input arguments and creates a new HelpCommand object
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Feature not recognized.\n" + HelpCommand.MESSAGE_USAGE;
    public static final String MESSAGE_PERSON_COMMANNDS = PERSON_COMMAND_WORD + ": Manages your contacts. Supported "
        + "subcommands:\n" + AddPersonCommand.COMMAND_WORD + ", " + EditPersonCommand.COMMAND_WORD + ", "
        + DeletePersonCommand.COMMAND_WORD + ", " + ClearPersonCommand.COMMAND_WORD + ", "
        + FindPersonCommand.COMMAND_WORD + ", " + FilterPersonCommand.COMMAND_WORD + ", "
        + ListPersonCommand.COMMAND_WORD + ", " + InfoPersonCommand.COMMAND_WORD;

    public static final String MESSAGE_TODO_COMMANDS = TODO_COMMAND_WORD
        + ": Manages your todos. Supported subcommands:\n" + AddTodoCommand.COMMAND_WORD + ", "
        + DeleteTodoCommand.COMMAND_WORD + ", " + DisplayTodoInformationCommand.COMMAND_WORD
        + ListTodoCommand.COMMAND_WORD + ", " + AddPersonToTodoCommand.COMMAND_WORD + ", "
        + RemovePersonFromTodoCommand.COMMAND_WORD + ", " + MarkTodoAsDoneCommand.COMMAND_WORD + ", "
        + MarkTodoAsNotDoneCommand.COMMAND_WORD;

    public static final String MESSAGE_EVENT_COMMANDS = EVENT_COMMAND_WORD
        + ": Manages your events. Supported subcommands:\n" + AddEventCommand.COMMAND_WORD + ", "
        + DeleteEventCommand.COMMAND_WORD + ", " + DisplayEventInformationCommand.COMMAND_WORD + ", "
        + ListEventCommand.COMMAND_WORD;

    /**
     * Parses the given {@code String} of arguments into a HelpCommand.
     * <p>
     * Valid argument lengths:
     * - 0: "help" -> ""
     * - 1: "help contact" -> "contact"
     * - 2: "help contact add" -> "contact add"
     * <p>
     * For invalid cases, defaults to the closest valid command:
     * - "help abcd" -> "help"
     * - "help contact abcd" -> "help contact"
     * - "help contact add abcd" -> "help contact add"
     * <p>
     * All trailing arguments that are not supported are immediately dropped without errors.
     *
     * @return HelpCommand that contains the message to display
     */
    @Override
    public HelpCommand parse(String args) {
        // Handle empty inputs -> help
        if (args.trim().isEmpty()) {
            return new HelpCommand(HelpCommand.MESSAGE_USAGE);
        }

        // Split command
        String[] argSplit = args.split("\\s+");
        String command = argSplit[0];
        String subcommand = argSplit.length > 1 ? argSplit[1] : "";

        // Check that top level command is valid
        switch (command) {
        case PERSON_COMMAND_WORD:
            return parsePersonHelp(subcommand);
        case TODO_COMMAND_WORD:
            return parseTodoHelp(subcommand);
        case EVENT_COMMAND_WORD:
            return parseEventHelp(subcommand);
        case EXIT_COMMAND_WORD:
        case BYE_COMMAND_WORD:
        case QUIT_COMMAND_WORD:
        case KILL_COMMAND_WORD:
            // Ignore all trailing arguments
            return new HelpCommand(ExitCommand.MESSAGE_USAGE);
        default:
            // No match, default to HelpCommand
            return new HelpCommand("Feature " + command + " not recognized.\n" + HelpCommand.MESSAGE_USAGE);
        }
    }

    /**
     * @param subcommand The subcommand to parse
     * @return HelpCommand that contains the message to display
     */
    public HelpCommand parsePersonHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return null;
        }

        switch (subcommand) {
        case AddPersonCommand.COMMAND_WORD:
            return new HelpCommand(AddPersonCommand.MESSAGE_USAGE);
        case EditPersonCommand.COMMAND_WORD:
            return new HelpCommand(EditPersonCommand.MESSAGE_USAGE);
        case DeletePersonCommand.COMMAND_WORD:
            return new HelpCommand(DeletePersonCommand.MESSAGE_USAGE);
        case ClearPersonCommand.COMMAND_WORD:
            return new HelpCommand(ClearPersonCommand.MESSAGE_USAGE);
        // To be deprecated soon
        case FindPersonCommand.COMMAND_WORD:
            return new HelpCommand(FindPersonCommand.MESSAGE_USAGE);
        case FilterPersonCommand.COMMAND_WORD:
            return new HelpCommand(FilterPersonCommand.MESSAGE_USAGE);
        case ListPersonCommand.COMMAND_WORD:
            return new HelpCommand(ListPersonCommand.MESSAGE_USAGE);
        case InfoPersonCommand.COMMAND_WORD:
            return new HelpCommand(InfoPersonCommand.MESSAGE_USAGE);
        default:
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + null);
        }
    }


    public HelpCommand parseTodoHelp(String subcommand) {

    }

    public HelpCommand parseEventHelp(String subcommand) {

    }
}
