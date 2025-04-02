package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.HelpCommandParser.getContactCommandsMessage;
import static seedu.address.logic.parser.HelpCommandParser.getTodoCommandsMessage;
import static seedu.address.logic.parser.HelpCommandParser.getEventCommandsMessage;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(
                "Very funny. Here you go.\n" + HelpCommand.MESSAGE_USAGE, false, false);
        assertCommandSuccess(new HelpCommand("Very funny. Here you go.\n" + HelpCommand.MESSAGE_USAGE),
                model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_contact_success() {
        CommandResult expectedCommandResult = new CommandResult(getContactCommandsMessage(), false, false);
        assertCommandSuccess(new HelpCommand(getContactCommandsMessage()), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_todo_success() {
        CommandResult expectedCommandResult = new CommandResult(getTodoCommandsMessage(), false, false);
        assertCommandSuccess(new HelpCommand(getTodoCommandsMessage()), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_event_success() {
        CommandResult expectedCommandResult = new CommandResult(getEventCommandsMessage(), false, false);
        assertCommandSuccess(new HelpCommand(getEventCommandsMessage()), model, expectedCommandResult, expectedModel);
    }
}
