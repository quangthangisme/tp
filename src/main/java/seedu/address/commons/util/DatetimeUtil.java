package seedu.address.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A container for datetime utilities.
 */
public class DatetimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");

    /**
     * Parse a datetime string.
     */
    public static LocalDateTime parse(String string) {
        try {
            LocalDateTime datetime = LocalDateTime.parse(string, DATE_TIME_FORMATTER);

            String formattedDatetime = datetime.format(DATE_TIME_FORMATTER);
            if (string.equals(formattedDatetime)) {
                return datetime;
            } else {
                throw new IllegalArgumentException("Invalid datetime: " + string);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime: " + string);
        }
    }
}
