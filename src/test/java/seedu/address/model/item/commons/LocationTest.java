package seedu.address.model.item.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValid() {
        // null name
        assertThrows(NullPointerException.class, () -> Location.isValid(null));

        // invalid name
        assertFalse(Location.isValid("")); // empty string
        assertFalse(Location.isValid(" ")); // spaces only
        assertFalse(Location.isValid("-^")); // starts with a hyphen
        assertFalse(Location.isValid("lol -house*")); // contains a word starting with a hyphen

        // valid name
        assertTrue(Location.isValid("nus Science")); // alphabets only
        assertTrue(Location.isValid("12345")); // numbers only
        assertTrue(Location.isValid("SCIENCE-s16"));
        assertTrue(Location.isValid("SINGAPORE SINGAPORE SINGAPORE SINGAPORE")); // long string
    }

    @Test
    public void equals() {
        Location name = new Location("Valid Location");

        // same values -> returns true
        assertTrue(name.equals(new Location("Valid Location")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Location("Other Valid Location")));
    }
}
