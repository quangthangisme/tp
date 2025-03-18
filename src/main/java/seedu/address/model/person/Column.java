package seedu.address.model.person;

/**
 * Represents the different columns or attributes of a Person that can be used for filtering.
 * Each column has an associated abbreviation for easy reference.
 */
public enum Column {
    NAME("n"),
    PHONE("p"),
    EMAIL("e"),
    ID("i"),
    COURSE("c"),
    GROUP("g"),
    TAG("t");

    private final String abbrev;

    /**
     * Constructs a Column with the given abbreviation.
     *
     * @param abbrev the abbreviation for the column
     */
    Column(String abbrev) {
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
