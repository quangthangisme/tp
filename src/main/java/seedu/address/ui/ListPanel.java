package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.util.DisplayableItem;

/**
 * Panel containing the unified list that can display contacts, events, or todos.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<DisplayableItem> listView;

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList}.
     */
    public ListPanel(ObservableList<DisplayableItem> itemList) {
        super(FXML);
        listView.setItems(itemList);
        listView.setCellFactory(listView -> new DisplayableListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of displayable items.
     */
    static class DisplayableListViewCell extends ListCell<DisplayableItem> {
        @Override
        protected void updateItem(DisplayableItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(((UiPart<Region>) item.getDisplayCard(getIndex() + 1)).getRoot());
            }
        }
    }
}
