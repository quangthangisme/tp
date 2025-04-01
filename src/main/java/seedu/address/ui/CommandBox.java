package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private final List<String> commandHistory;
    private int commandHistoryPointer;
    private String currentCommandInput;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        this.commandHistory = new ArrayList<>();
        this.commandHistoryPointer = 0;
        this.currentCommandInput = "";

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    /**
     * Handles key press events for command history navigation.
     */
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP) {
            navigateCommandHistory(true);
            keyEvent.consume();
        } else if (keyEvent.getCode() == KeyCode.DOWN) {
            navigateCommandHistory(false);
            keyEvent.consume();
        }
    }

    /**
     * Navigates through command history.
     * @param isUp true to navigate up (older), false to navigate down (newer)
     */
    private void navigateCommandHistory(boolean isUp) {
        // Save current input if at starting position
        if (commandHistoryPointer == 0) {
            currentCommandInput = commandTextField.getText();
        }

        if (isUp && commandHistoryPointer < commandHistory.size()) {
            commandHistoryPointer++;
            commandTextField.setText(commandHistory.get(commandHistory.size() - commandHistoryPointer));
            commandTextField.positionCaret(commandTextField.getText().length());
        } else if (!isUp && commandHistoryPointer > 0) {
            commandHistoryPointer--;
            if (commandHistoryPointer == 0) {
                commandTextField.setText(currentCommandInput);
            } else {
                commandTextField.setText(commandHistory.get(commandHistory.size() - commandHistoryPointer));
            }
            commandTextField.positionCaret(commandTextField.getText().length());
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.isEmpty()) {
            return;
        }

        try {
            commandExecutor.execute(commandText);

            if (!commandText.trim().isEmpty()) {
                commandHistory.add(commandText);
            }
            commandHistoryPointer = 0;
            currentCommandInput = "";

            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
