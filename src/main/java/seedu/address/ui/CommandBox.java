package seedu.address.ui;

import java.util.ArrayDeque;
import java.util.Deque;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;

    private final Deque<String> commandHistory;
    private final Deque<String> forwardHistory;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;

        this.commandHistory = new ArrayDeque<>();
        this.forwardHistory = new ArrayDeque<>();

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
        if (isUp) {
            if (!commandHistory.isEmpty()) {
                forwardHistory.push(commandTextField.getText());

                String previousCommand = commandHistory.pop();
                commandTextField.setText(previousCommand);
                commandTextField.positionCaret(previousCommand.length());
            }
        } else {
            if (!forwardHistory.isEmpty()) {
                if (!commandTextField.getText().isEmpty()) {
                    commandHistory.push(commandTextField.getText());
                }

                String nextCommand = forwardHistory.pop();
                commandTextField.setText(nextCommand);
                commandTextField.positionCaret(nextCommand.length());
            }
        }

    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            if (!commandText.trim().isEmpty()) {
                commandHistory.push(commandText);
            }

            commandExecutor.execute(commandText);

            forwardHistory.clear();
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
