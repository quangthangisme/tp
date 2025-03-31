package seedu.address.model.item;

import java.util.Set;

import seedu.address.model.item.commons.Tag;

/**
 * Represents an item that can be tagged in TutorConnect.
 */
public interface TaggedItem extends Item {
    Set<Tag> getTags();
}
