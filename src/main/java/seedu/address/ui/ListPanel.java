package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.card.Card;
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
     * Creates a {@code ListPanel} with the given {@code ObservableList} and click handler.
     */
    public ListPanel(ObservableList<DisplayableItem> itemList, Consumer<Integer> onItemClickHandler) {
        super(FXML);
        listView.setItems(itemList);
        listView.setCellFactory(listView -> new DisplayableListViewCell(onItemClickHandler));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of displayable items.
     */
    static class DisplayableListViewCell extends ListCell<DisplayableItem> {
        private final Consumer<Integer> onItemClickHandler;

        public DisplayableListViewCell(Consumer<Integer> onItemClickHandler) {
            this.onItemClickHandler = onItemClickHandler;
        }

        @Override
        protected void updateItem(DisplayableItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                UiPart<Region> uiPart = (UiPart<Region>) item.getDisplayCard(getIndex() + 1);
                setGraphic(uiPart.getRoot());

                if (uiPart instanceof Card && onItemClickHandler != null) {
                    Card<?> card = (Card<?>) uiPart;
                    final int index = getIndex() + 1;
                    card.setOnMouseClicked(() -> onItemClickHandler.accept(index));
                }
            }
        }
    }
}
