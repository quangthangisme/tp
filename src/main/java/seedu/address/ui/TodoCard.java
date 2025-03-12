package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.todo.Todo;

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

    /**
     * Creates a {@code TodoCard} with the given {@code todo} and index to display.
     */
    public TodoCard(Todo todo, int displayedIndex) {
        super(FXML);
        this.todo = todo;
        id.setText(displayedIndex + ". ");
        name.setText(todo.getName().name);
        deadline.setText(todo.getDeadline().toString());
        todoLocation.setText(todo.getLocation().value);
    }
}
