package seedu.address.logic.parser.event;

import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple event commands.
 */
public class EventCliSyntax {

    /* Long prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME_LONG = new Prefix("--name");
    public static final Prefix PREFIX_EVENT_START_LONG = new Prefix("--start");
    public static final Prefix PREFIX_EVENT_END_LONG = new Prefix("--end");
    public static final Prefix PREFIX_EVENT_LOCATION_LONG = new Prefix("--location");
    public static final Prefix PREFIX_EVENT_TAG_LONG = new Prefix("--tag");
    public static final Prefix PREFIX_EVENT_LINKED_CONTACT_LONG = new Prefix("--contact");

    /* Short prefix definitions */
    public static final Prefix PREFIX_EVENT_NAME_SHORT = new Prefix("-n");
    public static final Prefix PREFIX_EVENT_START_SHORT = new Prefix("-s");
    public static final Prefix PREFIX_EVENT_END_SHORT = new Prefix("-e");
    public static final Prefix PREFIX_EVENT_LOCATION_SHORT = new Prefix("-l");
    public static final Prefix PREFIX_EVENT_TAG_SHORT = new Prefix("-t");
    public static final Prefix PREFIX_EVENT_LINKED_CONTACT_SHORT = new Prefix("-c");
}

class NamePrefix extends PrefixAlias {
    public NamePrefix(String commandWord) {
        super(commandWord,
                EventCliSyntax.PREFIX_EVENT_NAME_LONG,
                EventCliSyntax.PREFIX_EVENT_NAME_SHORT
        );
    }

    @Override
    public String toString() {
        return String.format("%s / %s", getLong(), getShort());
    }
}

class StartPrefix extends PrefixAlias {
    public StartPrefix(String commandWord) {
        super(commandWord,
                EventCliSyntax.PREFIX_EVENT_START_LONG,
                EventCliSyntax.PREFIX_EVENT_START_SHORT
        );
    }

    @Override
    public String toString() {
        return String.format("%s / %s", getLong(), getShort());
    }
}

class EndPrefix extends PrefixAlias {
    public EndPrefix(String commandWord) {
        super(commandWord,
                EventCliSyntax.PREFIX_EVENT_END_LONG,
                EventCliSyntax.PREFIX_EVENT_END_SHORT
        );
    }
    @Override
    public String toString() {
        return String.format("%s / %s", getLong(), getShort());
    }
}

class LocationPrefix extends PrefixAlias {
    public LocationPrefix(String commandWord) {
        super(commandWord,
                EventCliSyntax.PREFIX_EVENT_LOCATION_LONG,
                EventCliSyntax.PREFIX_EVENT_LOCATION_SHORT
        );
    }
    @Override
    public String toString() {
        return String.format("%s / %s", getLong(), getShort());
    }
}

class TagPrefix extends PrefixAlias {
    public TagPrefix(String commandWord) {
        super(commandWord,
                EventCliSyntax.PREFIX_EVENT_TAG_LONG,
                EventCliSyntax.PREFIX_EVENT_TAG_SHORT
        );
    }
    @Override
    public String toString() {
        return String.format("%s / %s", getLong(), getShort());
    }
}

class ContactPrefix extends PrefixAlias {
    public ContactPrefix(String commandWord) {
        super(commandWord,
                EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG,
                EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_SHORT
        );
    }
    @Override
    public String toString() {
        return String.format("%s / %s", getLong(), getShort());
    }
}
