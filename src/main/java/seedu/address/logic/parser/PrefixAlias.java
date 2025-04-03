package seedu.address.logic.parser;


import seedu.address.commons.core.Pair;

/**
 * A class to handle the short and long flags for {@code Parser}
 */
public class PrefixAlias {
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

    @Override
    public String toString() {
        return String.format("%s / %s", prefixPair.first(), prefixPair.second());
    }
}
