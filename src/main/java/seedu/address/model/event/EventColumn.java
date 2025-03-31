package seedu.address.model.event;

/**
 * Represents the different columns or attributes of an Event that can be used for filtering.
 * Each column has an associated abbreviation for easy reference.
 */
public enum EventColumn {
    NAME("n"),
    START("s"),
    END("e"),
    LOCATION("l"),
    CONTACTS("c"),
    ATTENDANCE("a");

    private final String abbrev;

    /**
     * Constructs a Column with the given abbreviation.
     *
     * @param abbrev the abbreviation for the column
     */
    EventColumn(String abbrev) {
        this.abbrev = abbrev;
    }

    /**
     * Gets the abbreviation for this column.
     *
     * @return the abbreviation
     */
    public String getAbbrev() {
        return abbrev;
    }
}
