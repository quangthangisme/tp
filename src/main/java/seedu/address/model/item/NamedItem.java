package seedu.address.model.item;

import seedu.address.model.item.commons.Name;

/**
 * Represents an item that is named in TutorConnect.
 */
public interface NamedItem extends Item {
    Name getName();
}
