package seedu.address.model.person;

/**
 * Represents the logical operators that can be used in filter criteria.
 * These operators determine how multiple values in a filter criterion are combined.
 */
public enum Operator {
    AND("and"),
    OR("or"),
    NAND("nand"),
    NOR("nor");

    private final String name;

    /**
     * Constructs an Operator with the given name.
     *
     * @param name the string representation of the operator
     */
    Operator(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this operator.
     *
     * @return the name of the operator
     */
    public String getName() {
        return name;
    }
}
