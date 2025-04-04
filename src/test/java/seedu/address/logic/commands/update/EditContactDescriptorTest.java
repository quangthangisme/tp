package seedu.address.logic.commands.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ContactCommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.ContactCommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.ContactCommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EditContactDescriptorBuilder;

public class EditContactDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditContactDescriptor descriptorWithSameValues = new EditContactDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditContactDescriptor editedAmy = new EditContactDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditContactDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditContactDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }

    @Test
    public void toStringMethod() {
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        String expected = EditContactDescriptor.class.getCanonicalName()
                + "{id=" + editContactDescriptor.getName().orElse(null)
                + ", name=" + editContactDescriptor.getName().orElse(null)
                + ", email=" + editContactDescriptor.getEmail().orElse(null)
                + ", course=" + editContactDescriptor.getCourse().orElse(null)
                + ", group=" + editContactDescriptor.getGroup().orElse(null)
                + ", tags=" + editContactDescriptor.getTags().orElse(null) + "}";
        assertEquals(expected, editContactDescriptor.toString());
    }
}
