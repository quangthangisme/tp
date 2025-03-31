package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DatetimeUtilTest {
    @Test
    public void parse() {
        assertEquals(LocalDateTime.of(2025, 12, 31, 23, 59), DatetimeUtil.parse("25-12-31 23:59"));
        assertEquals(LocalDateTime.of(2024, 2, 29, 23, 59), DatetimeUtil.parse("24-02-29 23:59"));

        assertThrows(IllegalArgumentException.class, () -> DatetimeUtil.parse("25-31-12 23:59"));
        assertThrows(IllegalArgumentException.class, () -> DatetimeUtil.parse("24-02-31 23:59"));
        assertThrows(IllegalArgumentException.class, () -> DatetimeUtil.parse("25-12-31    23:59"));
        assertThrows(IllegalArgumentException.class, () -> DatetimeUtil.parse("25/12/31 23:59"));
    }
}
