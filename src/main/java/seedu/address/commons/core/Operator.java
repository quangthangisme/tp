package seedu.address.commons.core;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents the logical operators that can be used in filter criteria. These operators determine
 * how multiple values in a filter criterion are combined.
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

    /**
     * Gets the corresponding stream operation for the operator.
     *
     * @return the corresponding stream operation
     */
    public <T> boolean apply(Stream<T> stream, Predicate<T> predicate) {
        switch (this) {
        case AND:
            return stream.allMatch(predicate);
        case OR:
            return stream.anyMatch(predicate);
        case NAND:
            return !stream.allMatch(predicate);
        case NOR:
            return stream.noneMatch(predicate);
        // Should never happen: guard clause in case of adding new operators
        default:
            throw new UnsupportedOperationException("Operator not supported: " + this);
        }
    }
}
