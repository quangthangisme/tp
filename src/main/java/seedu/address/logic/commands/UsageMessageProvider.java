package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.DisplayEventInformationCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
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
 * Provides help message functionality for all commands.
 */
public class UsageMessageProvider {

    public static final String UNKNOWN_FEATURE_MESSAGE = "Feature %s not recognized.";


    public String getMessageUsage(String feature) throws CommandException {

        if (feature.startsWith(TODO_COMMAND_WORD)) {
            return getMessageUsageTodo(feature.substring(
                    TODO_COMMAND_WORD.length()).trim());
        }

        if (feature.startsWith(EVENT_COMMAND_WORD)) {
            return getMessageUsageEvent(feature.substring(
                    EVENT_COMMAND_WORD.length()).trim());
        }

        switch (feature) {

        case "":
            return HelpCommand.SHOWING_HELP_MESSAGE;

        case AddPersonCommand.COMMAND_WORD:
            return AddPersonCommand.MESSAGE_USAGE;

        case EditPersonCommand.COMMAND_WORD:
            return EditPersonCommand.MESSAGE_USAGE;

        case DeletePersonCommand.COMMAND_WORD:
            return DeletePersonCommand.MESSAGE_USAGE;

        case ClearPersonCommand.COMMAND_WORD:
            return ClearPersonCommand.MESSAGE_USAGE;

        case FindPersonCommand.COMMAND_WORD:
            return FindPersonCommand.MESSAGE_USAGE;

        case FilterPersonCommand.COMMAND_WORD:
            return FilterPersonCommand.MESSAGE_USAGE;

        case ListPersonCommand.COMMAND_WORD:
            return ListPersonCommand.MESSAGE_USAGE;

        case InfoPersonCommand.COMMAND_WORD:
            return InfoPersonCommand.MESSAGE_USAGE;

        case ExitCommand.COMMAND_WORD_EXIT, ExitCommand.COMMAND_WORD_BYE,
             ExitCommand.COMMAND_WORD_QUIT, ExitCommand.COMMAND_WORD_KILL:
            return ExitCommand.MESSAGE_USAGE;

        case HelpCommand.COMMAND_WORD:
            return HelpCommand.MESSAGE_USAGE;

        default:
            throw new CommandException(String.format(
                    UNKNOWN_FEATURE_MESSAGE, feature));
        }
    }

    private String getMessageUsageTodo(String todoFeature) throws CommandException {

        switch (todoFeature) {

        case "":
            return String.join("\n\n",
                    AddTodoCommand.MESSAGE_USAGE,
                    DisplayTodoInformationCommand.MESSAGE_USAGE,
                    ListTodoCommand.MESSAGE_USAGE,
                    DeleteTodoCommand.MESSAGE_USAGE,
                    AddPersonToTodoCommand.MESSAGE_USAGE,
                    RemovePersonFromTodoCommand.MESSAGE_USAGE,
                    MarkTodoAsDoneCommand.MESSAGE_USAGE,
                    MarkTodoAsNotDoneCommand.MESSAGE_USAGE);

        case AddTodoCommand.COMMAND_WORD:
            return AddTodoCommand.MESSAGE_USAGE;

        case DisplayTodoInformationCommand.COMMAND_WORD:
            return DisplayTodoInformationCommand.MESSAGE_USAGE;

        case ListTodoCommand.COMMAND_WORD:
            return ListTodoCommand.MESSAGE_USAGE;

        case DeleteTodoCommand.COMMAND_WORD:
            return DeleteTodoCommand.MESSAGE_USAGE;

        case AddPersonToTodoCommand.COMMAND_WORD:
            return AddPersonToTodoCommand.MESSAGE_USAGE;

        case RemovePersonFromTodoCommand.COMMAND_WORD:
            return RemovePersonFromTodoCommand.MESSAGE_USAGE;

        case MarkTodoAsDoneCommand.COMMAND_WORD:
            return MarkTodoAsDoneCommand.MESSAGE_USAGE;

        case MarkTodoAsNotDoneCommand.COMMAND_WORD:
            return MarkTodoAsNotDoneCommand.MESSAGE_USAGE;

        default:
            throw new CommandException(String.format(
                    UNKNOWN_FEATURE_MESSAGE, todoFeature));
        }
    }

    private String getMessageUsageEvent(String eventFeature) throws CommandException {

        switch (eventFeature) {

        case "":
            return String.join("\n\n",
                    AddEventCommand.MESSAGE_USAGE,
                    DisplayEventInformationCommand.MESSAGE_USAGE,
                    ListEventCommand.MESSAGE_USAGE,
                    DeleteEventCommand.MESSAGE_USAGE);

        case AddEventCommand.COMMAND_WORD:
            return AddEventCommand.MESSAGE_USAGE;

        case DisplayEventInformationCommand.COMMAND_WORD:
            return DisplayEventInformationCommand.MESSAGE_USAGE;

        case ListEventCommand.COMMAND_WORD:
            return ListEventCommand.MESSAGE_USAGE;

        case DeleteEventCommand.COMMAND_WORD:
            return DeleteEventCommand.MESSAGE_USAGE;

        default:
            throw new CommandException(String.format(
                    UNKNOWN_FEATURE_MESSAGE, eventFeature));
        }
    }
}
