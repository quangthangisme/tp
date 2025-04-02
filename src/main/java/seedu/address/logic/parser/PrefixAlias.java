package seedu.address.logic.parser;


import seedu.address.commons.core.Pair;

/**
 * A wrapper class to handle the short and long flags for {@code Parser}
 */
public abstract class PrefixAlias {
    private final Pair<Prefix, Prefix> prefixPair;

    /**
     * Creates a new PrefixAlias between short and long form of the prefix.
     */
    public PrefixAlias(Prefix longPrefix, Prefix shortPrefix) {
        this.prefixPair = new Pair<>(longPrefix, shortPrefix);
    }

    public Prefix getLong() {
        return prefixPair.first();
    }

    public Prefix getShort() {
        return prefixPair.second();
    }

    public Prefix[] getAll() {
        return new Prefix[]{getLong(), getShort()};
    }
}
