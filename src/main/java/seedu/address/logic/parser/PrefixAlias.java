package seedu.address.logic.parser;

import java.util.Optional;

import seedu.address.commons.core.Pair;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A wrapper class to handle the short and long flags for {@code Parser}
 */
public abstract class PrefixAlias {
    private final String commandName;
    private final Pair<Prefix, Prefix> prefixPair;

    /**
     * Creates a new PrefixAlias between short and long form of the prefix.
     */
    public PrefixAlias(String commandName, Prefix longPrefix, Prefix shortPrefix) {
        this.commandName = commandName;
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

    /**
     * Gets the values of the value of either the long or short prefix.
     */
    public Optional<String> getValue(ArgumentMultimap map) throws ParseException {
        boolean isLong = map.arePrefixesPresent(getLong());
        boolean isShort = map.arePrefixesPresent(getShort());
        if (isLong && isShort) {
            throw new ParseException(String.format(
                    "Cannot use both flags %s and %s for the flag: '%s'",
                    getLong(), getShort(), commandName
                )
            );
        }
        if (!map.arePrefixesPresent(getLong()) && !map.arePrefixesPresent(getShort())) {
            throw new ParseException("Missing required field: " + commandName);
        }
        return isLong ? map.getValue(getLong()) : map.getValue(getShort());
    }
}
