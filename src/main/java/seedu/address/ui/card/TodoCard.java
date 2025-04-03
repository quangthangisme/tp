package seedu.address.ui.card;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.todo.Todo;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Todo}.
 */
public class TodoCard extends UiPart<Region> implements Card<Todo> {

    private static final String FXML = "TodoListCard.fxml";
    private static final int MAX_TAG_LENGTH = 75;

    public final Todo todo;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private Label todoLocation;
    @FXML
    private Label todoStatus;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane contacts;

    /**
     * Creates a {@code TodoCard} with the given {@code todo} and index to display.
     */
    public TodoCard(Todo todo, int displayedIndex) {
        super(FXML);
        this.todo = todo;
        id.setText(displayedIndex + ". ");
        name.setText(todo.getName().value);
        todoStatus.setText("");
        todoStatus.setGraphic(createBoldLabel("Status: ", todo.getStatus().toString()));
        todoLocation.setText("");
        todoLocation.setGraphic(createBoldLabel("Location: ", todo.getLocation().value));
        deadline.setText("");
        deadline.setGraphic(createBoldLabel("Due at: ", todo.getDeadline().toString()));
        todo.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        todo.getContacts().stream()
                .sorted(Comparator.comparing(contact -> contact.getName().value))
                .forEach(contact -> contacts.getChildren().add(createTagLabel(contact.getName().value)));
    }

    /**
     * Creates a label for a tag, abbreviating if necessary.
     * @param tagText The text of the tag
     * @return A Label with the tag text, abbreviated if longer than MAX_TAG_LENGTH
     */
    private Label createTagLabel(String tagText) {
        if (tagText.length() <= MAX_TAG_LENGTH) {
            return new Label(tagText);
        } else {
            return new Label(tagText.substring(0, MAX_TAG_LENGTH - 3) + "...");
        }
    }

    /**
     * Creates a label with the first part bold and the second part normal.
     * @param boldPart The text that should be bold
     * @param normalPart The text that should be normal weight
     * @return An HBox containing the formatted text
     */
    private HBox createBoldLabel(String boldPart, String normalPart) {
        Label boldLabel = new Label(boldPart);
        boldLabel.setStyle("-fx-font-weight: bold");

        Label normalLabel = new Label(normalPart);

        HBox container = new HBox();
        container.getChildren().addAll(boldLabel, normalLabel);
        return container;
    }

    @Override
    public Todo getEntity() {
        return todo;
    }

    @Override
    public UiPart<Region> getUiPart() {
        return this;
    }

    @Override
    public void setOnMouseClicked(Runnable handler) {
        cardPane.setOnMouseClicked(event -> handler.run());
    }
}
