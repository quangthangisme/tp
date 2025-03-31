package seedu.address.model.item.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DatetimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Datetime(null));
    }

    @Test
    public void constructor_invalidDatetime_throwsIllegalArgumentException() {
        String invalidDatetime = "";
        assertThrows(IllegalArgumentException.class, () -> new Datetime(invalidDatetime));
    }

    @Test
    public void isValid() {
        // null name
        assertThrows(NullPointerException.class, () -> Datetime.isValid(null));

        // invalid name
        assertFalse(Datetime.isValid("")); // empty string
        assertFalse(Datetime.isValid(" ")); // spaces only
        assertFalse(Datetime.isValid("25-31-12 23:59"));
        assertFalse(Datetime.isValid("24-02-31 23:59"));
        assertFalse(Datetime.isValid("25-12-31    23:59"));
        assertFalse(Datetime.isValid("25/12/31 23:59"));

        // valid name
        assertTrue(Datetime.isValid("25-12-31 23:59"));
        assertTrue(Datetime.isValid("24-02-29 23:59"));
    }

    @Test
    public void equals() {
        Datetime name = new Datetime("25-12-31 23:59");

        // same values -> returns true
        assertTrue(name.equals(new Datetime("25-12-31 23:59")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Datetime("24-02-29 23:59")));
    }
}
