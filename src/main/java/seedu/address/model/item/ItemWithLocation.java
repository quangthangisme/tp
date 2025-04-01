package seedu.address.model.item;

import seedu.address.model.item.commons.Location;

/**
 * Represents an item that has a location in TutorConnect.
 */
public interface ItemWithLocation extends Item {
    Location getLocation();
}
