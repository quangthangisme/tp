package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.EVENT_COMMAND_WORD;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.read.ListContactCommand;
import seedu.address.logic.commands.read.ListEventCommand;
import seedu.address.logic.commands.read.ListTodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.todo.Todo;
import seedu.address.ui.util.DisplayableItem;
import seedu.address.ui.util.ListConverter;

/**
 * The Main Window. Provides the basic application layout.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Stage primaryStage;
    private Logic logic;
    // Independent Ui parts residing in this Ui container
    private ListPanel listPanel;
    private ListPanelViewType currentViewMode = ListPanelViewType.CONTACT;
    private ResultDisplay resultDisplay;
    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private StackPane listPanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private Button contactButton;
    @FXML
    private Button eventButton;
    @FXML
    private Button todoButton;
    @FXML
    private Button resetButton;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        commandBox.setViewSwitchHandler(this::switchView);

        switchView(ListPanelViewType.CONTACT);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Switches view based on the command prefix or buttons
     */
    private void switchView(ListPanelViewType viewType) {
        currentViewMode = viewType;
        switch (viewType) {
        case CONTACT:
            ObservableList<Contact> contactList = logic.getFilteredContactList();
            ObservableList<DisplayableItem> displayableContacts = ListConverter.toDisplayableContactList(contactList);
            listPanel = new ListPanel(displayableContacts);
            break;
        case EVENT:
            ObservableList<Event> eventList = logic.getFilteredEventList();
            ObservableList<DisplayableItem> displayableEvents = ListConverter.toDisplayableEventList(eventList);
            listPanel = new ListPanel(displayableEvents);
            break;
        case TODO:
            ObservableList<Todo> todoList = logic.getFilteredTodoList();
            ObservableList<DisplayableItem> displayableTodos = ListConverter.toDisplayableTodoList(todoList);
            listPanel = new ListPanel(displayableTodos);
            break;
        default:
            logger.warning("Invalid view type: " + viewType);
            return;
        }

        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(listPanel.getRoot());

        updateButtonStyles();
    }

    /**
     * Handles switching to contact view
     */
    @FXML
    private void handleContactButton() {
        switchView(ListPanelViewType.CONTACT);
    }

    /**
     * Handles switching to event view
     */
    @FXML
    private void handleEventButton() {
        switchView(ListPanelViewType.EVENT);

    }

    /**
     * Handles switching to todo view
     */
    @FXML
    private void handleTodoButton() {
        switchView(ListPanelViewType.TODO);

    }

    /**
     * Handles resetting filter (shows all items in the current view)
     */
    @FXML
    private void handleResetButton() {
        logger.info("Reset button clicked, showing all items for current view");

        try {
            switch (currentViewMode) {
            case CONTACT:
                executeCommand(String.format("%s %s", CONTACT_COMMAND_WORD, ListContactCommand.COMMAND_WORD));
                break;
            case EVENT:
                executeCommand(String.format("%s %s", EVENT_COMMAND_WORD, ListEventCommand.COMMAND_WORD));
                break;
            case TODO:
                executeCommand(String.format("%s %s", TODO_COMMAND_WORD, ListTodoCommand.COMMAND_WORD));
                break;
            default:
                return;
            }
            refreshCurrentView();

            logger.info("Reset successful");

        } catch (CommandException | ParseException e) {
            logger.info("Reset operation failed: " + e.getMessage());
            resultDisplay.setFeedbackToUser(e.getMessage());
        }
    }

    /**
     * Update the styling of buttons based on current view
     */
    private void updateButtonStyles() {
        // Remove active class from all buttons
        contactButton.getStyleClass().remove("active-button");
        eventButton.getStyleClass().remove("active-button");
        todoButton.getStyleClass().remove("active-button");

        // Add active class to currently selected button
        switch (currentViewMode) {
        case CONTACT:
            contactButton.getStyleClass().add("active-button");
            break;
        case EVENT:
            eventButton.getStyleClass().add("active-button");
            break;
        case TODO:
            todoButton.getStyleClass().add("active-button");
            break;
        default:
            break;
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isViewSwitchNeeded()) {
                handleViewSwitching(commandResult.getViewType());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            refreshCurrentView();

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles view switching based on the ViewType from CommandResult
     */
    private void handleViewSwitching(ListPanelViewType viewType) {
        switch (viewType) {
        case CONTACT:
            switchView(ListPanelViewType.CONTACT);
            break;
        case EVENT:
            switchView(ListPanelViewType.EVENT);
            break;
        case TODO:
            switchView(ListPanelViewType.TODO);
            break;
        default:
            break;
        }
    }

    /**
     * Refreshes the current view to reflect any changes in data
     */
    private void refreshCurrentView() {
        switch (currentViewMode) {
        case CONTACT:
            switchView(ListPanelViewType.CONTACT);
            break;
        case EVENT:
            switchView(ListPanelViewType.EVENT);
            break;
        case TODO:
            switchView(ListPanelViewType.TODO);
            break;
        default:
            break;
        }
    }
}
