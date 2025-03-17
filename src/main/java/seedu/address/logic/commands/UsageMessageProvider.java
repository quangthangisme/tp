package seedu.address.logic.commands;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.commands.event.DeleteEventCommand;
import seedu.address.logic.commands.event.DisplayEventInformationCommand;
import seedu.address.logic.commands.event.ListEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.*;
import seedu.address.logic.commands.todo.*;

import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

public class UsageMessageProvider {

    public static final String UNKNOWN_FEATURE_MESSAGE = "Feature %s not recognized.";

    public String getMessageUsage(String feature) throws CommandException {

        if (feature.startsWith(TODO_COMMAND_WORD)) {
            return getMessageUsageTodo(feature);
        }

        if (feature.startsWith(EVENT_COMMAND_WORD)) {
            return getMessageUsageEvent(feature);
        }

        switch (feature) {

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

        switch (todoFeature.split(" ")[1]) {

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

            default:
                throw new CommandException(String.format(
                        UNKNOWN_FEATURE_MESSAGE, todoFeature));
        }
    }

    private String getMessageUsageEvent(String eventFeature) throws CommandException {

        switch (eventFeature.split(" ")[1]) {

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
