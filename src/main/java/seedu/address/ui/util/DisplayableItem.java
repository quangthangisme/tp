package seedu.address.ui.util;

import seedu.address.ui.UiPart;

/**
 * Represents an item that can be displayed in the UI list.
 */
public interface DisplayableItem {
    /**
     * Creates a UI card representing this item.
     *
     * @param index The display index for this item.
     * @return The UiPart that can render this item.
     */
    UiPart<?> getDisplayCard(int index);
}
