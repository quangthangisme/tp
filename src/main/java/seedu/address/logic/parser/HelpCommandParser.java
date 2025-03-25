package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.BYE_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EXIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.HELP_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.KILL_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.QUIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.AddPersonToEventCommand;
import seedu.address.logic.commands.event.AddPersonToLogEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.DisplayEventInformationCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.event.RemovePersonFromEventCommand;
import seedu.address.logic.commands.event.RemovePersonFromLogEventCommand;
import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.ClearPersonCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.logic.commands.person.InfoPersonCommand;
import seedu.address.logic.commands.person.ListPersonCommand;
import seedu.address.logic.commands.todo.AddPersonToTodoCommand;
import seedu.address.logic.commands.todo.AddTagToTodoCommand;
import seedu.address.logic.commands.todo.AddTodoCommand;
import seedu.address.logic.commands.todo.DeleteTodoCommand;
import seedu.address.logic.commands.todo.DisplayTodoInformationCommand;
import seedu.address.logic.commands.todo.ListTodoCommand;
import seedu.address.logic.commands.todo.MarkTodoAsDoneCommand;
import seedu.address.logic.commands.todo.MarkTodoAsNotDoneCommand;
import seedu.address.logic.commands.todo.RemovePersonFromTodoCommand;
import seedu.address.logic.commands.todo.RemoveTagFromTodoCommand;

/**
 * Parses input arguments and creates a new HelpCommand object.
 *
 * <p>
 * This parser handles the "help" command and its subcommands for different feature categories:
 * - Person: Manages your contacts.
 * - Todo: Manages your todos.
 * - Event: Manages your events.
 *
 * <p>For each category, specific subcommands are supported, and their usage instructions can
 * be displayed using the help command with the appropriate arguments.
 *
 * <p>For invalid inputs, the closest valid command usage is returned. All trailing arguments
 * that are not supported are immediately dropped without errors.
 */
public class HelpCommandParser implements Parser<HelpCommand> {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Feature not recognized.\n" + HelpCommand.MESSAGE_USAGE;
    public static final String MESSAGE_PERSON_COMMANDS = PERSON_COMMAND_WORD
        + ": Manages your contacts. Supported subcommands:\n"
        + AddPersonCommand.COMMAND_WORD + ", "
        + EditPersonCommand.COMMAND_WORD + ", "
        + DeletePersonCommand.COMMAND_WORD + ", "
        + ClearPersonCommand.COMMAND_WORD + ", "
        + FindPersonCommand.COMMAND_WORD + ", "
        + FilterPersonCommand.COMMAND_WORD + ", "
        + ListPersonCommand.COMMAND_WORD + ", "
        + InfoPersonCommand.COMMAND_WORD;

    public static final String MESSAGE_TODO_COMMANDS = TODO_COMMAND_WORD
        + ": Manages your todos. Supported subcommands:\n"
        + AddTodoCommand.COMMAND_WORD + ", "
        + DeleteTodoCommand.COMMAND_WORD + ", "
        + DisplayTodoInformationCommand.COMMAND_WORD + ", "
        + ListTodoCommand.COMMAND_WORD + ", "
        + AddPersonToTodoCommand.COMMAND_WORD + ", "
        + RemovePersonFromTodoCommand.COMMAND_WORD + ", "
        + MarkTodoAsDoneCommand.COMMAND_WORD + ", "
        + MarkTodoAsNotDoneCommand.COMMAND_WORD + ", "
        + AddTagToTodoCommand.COMMAND_WORD + ", "
        + RemoveTagFromTodoCommand.COMMAND_WORD;

    public static final String MESSAGE_EVENT_COMMANDS = EVENT_COMMAND_WORD
        + ": Manages your events. Supported subcommands:\n"
        + AddEventCommand.COMMAND_WORD + ", "
        + DeleteEventCommand.COMMAND_WORD + ", "
        + DisplayEventInformationCommand.COMMAND_WORD + ", "
        + ListEventCommand.COMMAND_WORD + ", "
        + AddPersonToEventCommand.COMMAND_WORD + ", "
        + RemovePersonFromEventCommand.COMMAND_WORD + ", "
        + AddPersonToLogEventCommand.COMMAND_WORD + ", "
        + RemovePersonFromLogEventCommand.COMMAND_WORD;

    /**
     * Parses the given {@code String} of arguments into a HelpCommand.
     *
     * <p>Valid argument lengths:
     * - 0: "help" -> ""
     * - 1: "help contact" -> "contact"
     * - 2: "help contact add" -> "contact add"
     *
     * <p>For invalid cases, defaults to the closest valid command:
     * - "help abcd" -> "help"
     * - "help contact abcd" -> "help contact"
     * - "help contact add abcd" -> "help contact add"
     *
     * <p>All trailing arguments that are not supported are immediately dropped without errors.
     *
     * @param args The input arguments.
     * @return HelpCommand that contains the message to display.
     */
    @Override
    public HelpCommand parse(String args) {
        // Handle empty inputs -> help
        if (args.trim().isEmpty()) {
            return new HelpCommand(HelpCommand.MESSAGE_USAGE);
        }

        // Split command
        String[] argSplit = args.trim().split("\\s+");
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
        case HELP_COMMAND_WORD:
            return new HelpCommand("Very funny. Here you go.\n" + HelpCommand.MESSAGE_USAGE);
        default:
            // No match, default to HelpCommand
            return new HelpCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private HelpCommand parsePersonHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(MESSAGE_PERSON_COMMANDS);
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
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + MESSAGE_PERSON_COMMANDS);
        }
    }


    private HelpCommand parseTodoHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(MESSAGE_TODO_COMMANDS);
        }

        switch (subcommand) {
        case AddTodoCommand.COMMAND_WORD:
            return new HelpCommand(AddTodoCommand.MESSAGE_USAGE);
        case DeleteTodoCommand.COMMAND_WORD:
            return new HelpCommand(DeleteTodoCommand.MESSAGE_USAGE);
        case DisplayTodoInformationCommand.COMMAND_WORD:
            return new HelpCommand(DisplayTodoInformationCommand.MESSAGE_USAGE);
        case ListTodoCommand.COMMAND_WORD:
            return new HelpCommand(ListTodoCommand.MESSAGE_USAGE);
        case AddPersonToTodoCommand.COMMAND_WORD:
            return new HelpCommand(AddPersonToTodoCommand.MESSAGE_USAGE);
        case RemovePersonFromTodoCommand.COMMAND_WORD:
            return new HelpCommand(RemovePersonFromTodoCommand.MESSAGE_USAGE);
        case MarkTodoAsDoneCommand.COMMAND_WORD:
            return new HelpCommand(MarkTodoAsDoneCommand.MESSAGE_USAGE);
        case MarkTodoAsNotDoneCommand.COMMAND_WORD:
            return new HelpCommand(MarkTodoAsNotDoneCommand.MESSAGE_USAGE);
        case AddTagToTodoCommand.COMMAND_WORD:
            return new HelpCommand(AddTagToTodoCommand.MESSAGE_USAGE);
        case RemoveTagFromTodoCommand.COMMAND_WORD:
            return new HelpCommand(RemoveTagFromTodoCommand.MESSAGE_USAGE);
        default:
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + MESSAGE_TODO_COMMANDS);
        }
    }


    private HelpCommand parseEventHelp(String subcommand) {
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(MESSAGE_EVENT_COMMANDS);
        }

        switch (subcommand) {
        case AddEventCommand.COMMAND_WORD:
            return new HelpCommand(AddEventCommand.MESSAGE_USAGE);
        case DeleteEventCommand.COMMAND_WORD:
            return new HelpCommand(DeleteEventCommand.MESSAGE_USAGE);
        case DisplayEventInformationCommand.COMMAND_WORD:
            return new HelpCommand(DisplayEventInformationCommand.MESSAGE_USAGE);
        case ListEventCommand.COMMAND_WORD:
            return new HelpCommand(ListEventCommand.MESSAGE_USAGE);
        case AddPersonToEventCommand.COMMAND_WORD:
            return new HelpCommand(AddPersonToEventCommand.MESSAGE_USAGE);
        case RemovePersonFromEventCommand.COMMAND_WORD:
            return new HelpCommand(RemovePersonFromEventCommand.MESSAGE_USAGE);
        case AddPersonToLogEventCommand.COMMAND_WORD:
            return new HelpCommand(AddPersonToLogEventCommand.MESSAGE_USAGE);
        case RemovePersonFromLogEventCommand.COMMAND_WORD:
            return new HelpCommand(RemovePersonFromLogEventCommand.MESSAGE_USAGE);
        default:
            return new HelpCommand("Subcommand " + subcommand + " not recognized.\n" + MESSAGE_EVENT_COMMANDS);
        }
    }
}
