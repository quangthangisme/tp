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
public class TodoCard extends UiPart<Region> {

    private static final String FXML = "TodoListCard.fxml";

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
    private FlowPane tags;

    /**
     * Creates a {@code TodoCard} with the given {@code todo} and index to display.
     */
    public TodoCard(Todo todo, int displayedIndex) {
        super(FXML);
        this.todo = todo;
        id.setText(displayedIndex + ". ");
        name.setText(todo.getName().value);
        deadline.setText(todo.getDeadline().toString());
        todoLocation.setText(todo.getLocation().value);
        todo.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
