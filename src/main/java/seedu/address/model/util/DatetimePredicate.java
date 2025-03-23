package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * A predicate that tests whether a given {@link LocalDateTime} falls within a specified start
 * and/or end time. The time range is specified using a custom string format: (startTime/endTime),
 * where either value can be "-" to indicate no bound.
 */
public class DatetimePredicate implements Predicate<LocalDateTime> {
    public static final String DATE_PATTERN = "\\([^)]*[^)\\s][^)]*/[^)]*[^)\\s][^)]*\\)";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yy-MM-dd "
            + "HH:mm");
    private static final String DASH = "-";
    private static final String INVALID_FORMAT_MESSAGE =
            "Invalid format. Must be start/end, -/end, or start/-.";
    private static final String INVALID_DATE_MESSAGE =
            "Invalid date format: ";
    private static final String AT_LEAST_ONE_REQUIRED_MESSAGE =
            "At least one of start or end time must be specified.";

    private final Optional<LocalDateTime> startTimeOpt;
    private final Optional<LocalDateTime> endTimeOpt;

    /**
     * Constructs a {@code DatetimePredicate} with optional start and end times.
     *
     * @param startTimeOpt The optional start time.
     * @param endTimeOpt   The optional end time.
     * @throws IllegalArgumentException if both start and end times are empty.
     */
    public DatetimePredicate(Optional<LocalDateTime> startTimeOpt,
                             Optional<LocalDateTime> endTimeOpt) {
        if (startTimeOpt.isEmpty() && endTimeOpt.isEmpty()) {
            throw new IllegalArgumentException(AT_LEAST_ONE_REQUIRED_MESSAGE);
        }
        this.startTimeOpt = startTimeOpt;
        this.endTimeOpt = endTimeOpt;
    }

    /**
     * Constructs a {@code DatetimePredicate} from a formatted string.
     *
     * @param predicateString The formatted string in the format (startTime/endTime), where "-"
     *                        represents an unspecified bound.
     * @throws IllegalArgumentException if the format is invalid or both start and end times are
     *                                  empty.
     */
    public DatetimePredicate(String predicateString) {
        String trimmed = predicateString.trim();
        if (!trimmed.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException(INVALID_FORMAT_MESSAGE);
        }

        String[] parts = trimmed.substring(1, trimmed.length() - 1).split("/");
        this.startTimeOpt = parseDatetime(parts[0]);
        this.endTimeOpt = parseDatetime(parts[1]);

        if (startTimeOpt.isEmpty() && endTimeOpt.isEmpty()) {
            throw new IllegalArgumentException(AT_LEAST_ONE_REQUIRED_MESSAGE);
        }
    }

    /**
     * Parses a datetime string into an {@link Optional} of {@link LocalDateTime}.
     *
     * @param value The string value to parse.
     * @return An {@code Optional} containing the parsed datetime, or empty if the input is "-".
     * @throws IllegalArgumentException if the datetime format is invalid.
     */
    private Optional<LocalDateTime> parseDatetime(String value) {
        value = value.trim();
        if (DASH.equals(value)) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDateTime.parse(value, FORMATTER));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE + value);
        }
    }

    /**
     * Tests whether the given {@code LocalDateTime} falls within the specified range.
     */
    @Override
    public boolean test(LocalDateTime time) {
        return startTimeOpt.map(startTime -> startTime.isBefore(time)).orElse(true)
                && endTimeOpt.map(endTime -> endTime.isAfter(time)).orElse(true);
    }

    /**
     * Checks if the given predicate string is valid.
     *
     * @param predicateString The predicate string to validate.
     * @return True if the string is valid, false otherwise.
     */
    public static boolean isValid(String predicateString) {
        try {
            new DatetimePredicate(predicateString);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DatetimePredicate otherPredicate)) {
            return false;
        }

        return startTimeOpt.equals(otherPredicate.startTimeOpt)
                && endTimeOpt.equals(otherPredicate.endTimeOpt);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("startTimeOpt", startTimeOpt)
                .add("endTimeOpt", endTimeOpt).toString();
    }
}
