package seedu.address.model.todo;

/**
 * Represents the different columns or attributes of a Todo that can be used for filtering.
 * Each column has an associated abbreviation for easy reference.
 */
public enum TodoColumn {
    NAME("n"),
    DEADLINE("d"),
    LOCATION("l"),
    STATUS("s"),
    CONTACTS("c");

    private final String abbrev;

    /**
     * Constructs a Column with the given abbreviation.
     *
     * @param abbrev the abbreviation for the column
     */
    TodoColumn(String abbrev) {
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
