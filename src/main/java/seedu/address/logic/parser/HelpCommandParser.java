package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.BYE_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EXIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.HELP_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.KILL_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.QUIT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import java.util.LinkedHashMap;
import java.util.Map;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.create.AddContactCommand;
import seedu.address.logic.commands.create.AddEventCommand;
import seedu.address.logic.commands.create.AddTodoCommand;
import seedu.address.logic.commands.delete.ClearContactCommand;
import seedu.address.logic.commands.delete.ClearEventCommand;
import seedu.address.logic.commands.delete.ClearTodoCommand;
import seedu.address.logic.commands.delete.DeleteContactCommand;
import seedu.address.logic.commands.delete.DeleteEventCommand;
import seedu.address.logic.commands.delete.DeleteTodoCommand;
import seedu.address.logic.commands.read.FilterContactCommand;
import seedu.address.logic.commands.read.FilterEventCommand;
import seedu.address.logic.commands.read.FilterTodoCommand;
import seedu.address.logic.commands.read.InfoContactCommand;
import seedu.address.logic.commands.read.InfoEventCommand;
import seedu.address.logic.commands.read.InfoTodoCommand;
import seedu.address.logic.commands.read.ListContactCommand;
import seedu.address.logic.commands.read.ListEventCommand;
import seedu.address.logic.commands.read.ListTodoCommand;
import seedu.address.logic.commands.update.AddContactToEventCommand;
import seedu.address.logic.commands.update.AddContactToLogEventCommand;
import seedu.address.logic.commands.update.AddContactToTodoCommand;
import seedu.address.logic.commands.update.AddTagToContactCommand;
import seedu.address.logic.commands.update.AddTagToEventCommand;
import seedu.address.logic.commands.update.AddTagToTodoCommand;
import seedu.address.logic.commands.update.EditContactCommand;
import seedu.address.logic.commands.update.EditTodoCommand;
import seedu.address.logic.commands.update.MarkTodoAsDoneCommand;
import seedu.address.logic.commands.update.MarkTodoAsNotDoneCommand;
import seedu.address.logic.commands.update.RemoveContactFromEventCommand;
import seedu.address.logic.commands.update.RemoveContactFromLogEventCommand;
import seedu.address.logic.commands.update.RemoveContactFromTodoCommand;
import seedu.address.logic.commands.update.RemoveTagFromContactCommand;
import seedu.address.logic.commands.update.RemoveTagFromEventCommand;
import seedu.address.logic.commands.update.RemoveTagFromTodoCommand;

/**
 * Parses input arguments and creates a new HelpCommand object.
 *
 * <p>
 * This parser handles the "help" command and its subcommands for different feature categories:
 * - Contact: Manages your contacts.
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

    // Linked HashMap preserves insertion order
    public static final Map<String, String> TODO_COMMAND_USAGE_MAP = new LinkedHashMap<>();
    public static final Map<String, String> EVENT_COMMAND_USAGE_MAP = new LinkedHashMap<>();
    public static final Map<String, String> CONTACT_COMMAND_USAGE_MAP = new LinkedHashMap<>();

    static {
        // Contact
        CONTACT_COMMAND_USAGE_MAP.put(AddContactCommand.COMMAND_WORD, AddContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(EditContactCommand.COMMAND_WORD, EditContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(DeleteContactCommand.COMMAND_WORD, DeleteContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(InfoContactCommand.COMMAND_WORD, InfoContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(ListContactCommand.COMMAND_WORD, ListContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(ClearContactCommand.COMMAND_WORD, ClearContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(FilterContactCommand.COMMAND_WORD, FilterContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(AddTagToContactCommand.COMMAND_WORD, AddTagToContactCommand.MESSAGE_USAGE);
        CONTACT_COMMAND_USAGE_MAP.put(RemoveTagFromContactCommand.COMMAND_WORD,
                RemoveTagFromContactCommand.MESSAGE_USAGE);

        // Todo
        TODO_COMMAND_USAGE_MAP.put(AddTodoCommand.COMMAND_WORD, AddTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(EditTodoCommand.COMMAND_WORD, EditTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(DeleteTodoCommand.COMMAND_WORD, DeleteTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(InfoTodoCommand.COMMAND_WORD, InfoTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(ListTodoCommand.COMMAND_WORD, ListTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(ClearTodoCommand.COMMAND_WORD, ClearTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(FilterTodoCommand.COMMAND_WORD, FilterTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(AddContactToTodoCommand.COMMAND_WORD, AddContactToTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(RemoveContactFromTodoCommand.COMMAND_WORD,
                RemoveContactFromTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(AddTagToTodoCommand.COMMAND_WORD, AddTagToTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(RemoveTagFromTodoCommand.COMMAND_WORD, RemoveTagFromTodoCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(MarkTodoAsDoneCommand.COMMAND_WORD, MarkTodoAsDoneCommand.MESSAGE_USAGE);
        TODO_COMMAND_USAGE_MAP.put(MarkTodoAsNotDoneCommand.COMMAND_WORD, MarkTodoAsNotDoneCommand.MESSAGE_USAGE);

        // Event
        EVENT_COMMAND_USAGE_MAP.put(AddEventCommand.COMMAND_WORD, AddEventCommand.MESSAGE_USAGE);
        // TODO: EVENT_COMMAND_USAGE_MAP.put(EditEventCommand.COMMAND_WORD, EditEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(DeleteEventCommand.COMMAND_WORD, DeleteEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(InfoEventCommand.COMMAND_WORD, InfoEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(ListEventCommand.COMMAND_WORD, ListEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(ClearEventCommand.COMMAND_WORD, ClearEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(FilterEventCommand.COMMAND_WORD, FilterEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(AddContactToEventCommand.COMMAND_WORD, AddContactToEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(RemoveContactFromEventCommand.COMMAND_WORD,
                RemoveContactFromEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(AddTagToEventCommand.COMMAND_WORD, AddTagToEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(RemoveTagFromEventCommand.COMMAND_WORD, RemoveTagFromEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(AddContactToLogEventCommand.COMMAND_WORD,
                AddContactToLogEventCommand.MESSAGE_USAGE);
        EVENT_COMMAND_USAGE_MAP.put(RemoveContactFromLogEventCommand.COMMAND_WORD,
                RemoveContactFromLogEventCommand.MESSAGE_USAGE);


    }

    public static String getContactCommandsMessage() {
        return CONTACT_COMMAND_WORD + ": Manages your contacts. Supported subcommands:\n" + String.join(", ",
                CONTACT_COMMAND_USAGE_MAP.keySet());
    }

    public static String getEventCommandsMessage() {
        return EVENT_COMMAND_WORD + ": Manages your events. Supported subcommands:\n" + String.join(", ",
                EVENT_COMMAND_USAGE_MAP.keySet());
    }

    public static String getTodoCommandsMessage() {
        return TODO_COMMAND_WORD + ": Manages your todos. Supported subcommands:\n" + String.join(", ",
                TODO_COMMAND_USAGE_MAP.keySet());
    }

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
        case CONTACT_COMMAND_WORD:
            return parseContactHelp(subcommand);
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

    private HelpCommand parseContactHelp(String subcommand) {
        // Empty -> help contact
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(getContactCommandsMessage());
        }
        // Non-empty -> help contact subcommand
        String message = CONTACT_COMMAND_USAGE_MAP.getOrDefault(subcommand,
                "Subcommand " + subcommand + " not recognized.\n" + getContactCommandsMessage());
        return new HelpCommand(message);
    }


    private HelpCommand parseTodoHelp(String subcommand) {
        // Empty -> help todo
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(getTodoCommandsMessage());
        }
        // Non-empty -> help todo subcommand
        String message = TODO_COMMAND_USAGE_MAP.getOrDefault(subcommand,
                "Subcommand " + subcommand + " not recognized.\n" + getTodoCommandsMessage());
        return new HelpCommand(message);
    }


    private HelpCommand parseEventHelp(String subcommand) {
        // Empty -> help event
        if (subcommand.trim().isEmpty()) {
            return new HelpCommand(getEventCommandsMessage());
        }
        // Non-empty -> help event subcommand
        String message = EVENT_COMMAND_USAGE_MAP.getOrDefault(subcommand,
                "Subcommand " + subcommand + " not recognized.\n" + getEventCommandsMessage());
        return new HelpCommand(message);
    }
}
