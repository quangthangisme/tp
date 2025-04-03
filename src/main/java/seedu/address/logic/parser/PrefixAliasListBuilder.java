package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a list of {@code PrefixAlias}, build the array
 * for the varargs for {@code ArgumentMultimap}
 */
public class PrefixAliasListBuilder {
    private final List<PrefixAlias> prefixes;

    /**
     * Construct a new {@code PrefixAliasListBuilder}
     */
    public PrefixAliasListBuilder() {
        prefixes = new ArrayList<>();
    }

    /**
     * Adds a single prefix and returns itself for continuous building.
     */
    public PrefixAliasListBuilder add(PrefixAlias prefix) {
        prefixes.add(prefix);
        return this;
    }

    /**
     * Adds multiple prefixes at once and returns itself for continuous building.
     */
    public PrefixAliasListBuilder add(PrefixAlias... prefixArray) {
        prefixes.addAll(List.of(prefixArray));
        return this;
    }

    /**
     * Returns the final built list.
     */
    public List<Prefix> getFullPrefixList() {
        List<Prefix> allPrefixes = new ArrayList<>();
        for (PrefixAlias alias : prefixes) {
            allPrefixes.add(alias.getLong());
            allPrefixes.add(alias.getShort());
        }
        return allPrefixes;
    }

    /**
     * Get the final array of {@code prefix}
     */
    public Prefix[] toArray() {
        return getFullPrefixList().toArray(new Prefix[0]);
    }
}
