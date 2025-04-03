package seedu.address.ui.card;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> implements Card<Event> {

    private static final String FXML = "EventListCard.fxml";
    private static final int MAX_TAG_LENGTH = 75;

    public final Event event;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label eventLocation;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code EventCard} with the given {@code event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().value);
        startTime.setText("Start time: " + event.getStartTime().toString());
        endTime.setText("End Time: " + event.getEndTime().toString());
        eventLocation.setText("Location: " + event.getLocation().toString());
        event.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(createTagLabel(tag.tagName)));
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

    @Override
    public Event getEntity() {
        return event;
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
