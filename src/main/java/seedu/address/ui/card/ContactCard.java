package seedu.address.ui.card;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.contact.Contact;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> implements Card<Contact> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private static final String COURSE_ICON = "/images/contact-card/course.png";
    private static final String GROUP_ICON = "/images/contact-card/group.png";
    private static final int MAX_TAG_LENGTH = 75;

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private Label uid;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label course;
    @FXML
    private Label group;

    // Add ImageView fields for icons
    @FXML
    private ImageView uidIcon;
    @FXML
    private ImageView emailIcon;
    @FXML
    private ImageView courseIcon;
    @FXML
    private ImageView groupIcon;

    /**
     * Creates a {@code ContactCode} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;
        id.setText(displayedIndex + ". ");
        name.setText(contact.getName().value);
        contact.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(createTagLabel(tag.tagName)));
        course.setText(contact.getCourse().fullCourse);
        group.setText(contact.getGroup().fullGroup);

        setIcons();
    }

    /**
     * Sets the icons for each field.
     */
    private void setIcons() {
        courseIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(COURSE_ICON))));
        groupIcon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(GROUP_ICON))));
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
    public Contact getEntity() {
        return contact;
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
