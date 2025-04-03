package seedu.address.ui.card;

import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * Interface for UI card components that display information for model entities.
 * @param <T> The type of entity this card displays
 */
public interface Card<T> {

    /**
     * Gets the underlying entity displayed by this card.
     *
     * @return The entity
     */
    T getEntity();

    /**
     * Gets the UiPart representing this card.
     *
     * @return The UiPart
     */
    UiPart<Region> getUiPart();
}
