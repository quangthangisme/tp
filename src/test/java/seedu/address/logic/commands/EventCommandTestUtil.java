package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.List;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.item.predicate.NamePredicate;

/**
 * Contains helper methods for testing event commands.
 */
public class EventCommandTestUtil {

    public static final String VALID_NAME_MEETING = "MEETING at ScHoOl";
    public static final String VALID_START_MEETING = "25-12-31 23:59";
    public static final String VALID_END_MEETING = "26-01-01 00:59";
    public static final String VALID_LOCATION_MEETING = "Prof office";
    public static final String VALID_TAG_MEETING = "why????";

    public static final String VALID_NAME_CRYING = "Crying over PhD";
    public static final String VALID_START_CRYING = "25-12-31 23:59";
    public static final String VALID_END_CRYING = "26-01-01 00:59";
    public static final String VALID_LOCATION_CRYING = "Prof office";
    public static final String VALID_TAG_CRYING = "sad";

    public static final String NAME_DESC_MEETING = " " + PREFIX_EVENT_NAME_LONG + VALID_NAME_MEETING;
    public static final String START_DESC_MEETING = " " + PREFIX_EVENT_START_LONG + VALID_START_MEETING;
    public static final String END_DESC_MEETING = " " + PREFIX_EVENT_END_LONG + VALID_END_MEETING;
    public static final String LOCATION_DESC_MEETING = " " + PREFIX_EVENT_LOCATION_LONG + VALID_LOCATION_MEETING;
    public static final String TAG_DESC_MEETING = " " + PREFIX_EVENT_TAG_LONG + VALID_TAG_MEETING;
    public static final String NAME_DESC_CRYING = " " + PREFIX_EVENT_NAME_LONG + VALID_NAME_CRYING;
    public static final String START_DESC_CRYING = " " + PREFIX_EVENT_START_LONG + VALID_START_CRYING;
    public static final String END_DESC_CRYING = " " + PREFIX_EVENT_END_LONG + VALID_END_CRYING;
    public static final String LOCATION_DESC_CRYING = " " + PREFIX_EVENT_LOCATION_LONG + VALID_LOCATION_CRYING;
    public static final String TAG_DESC_CRYING = " " + PREFIX_EVENT_TAG_LONG + VALID_TAG_CRYING;

    // & not allowed
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME_LONG + "Stuff&stuff";
    // Not datetime
    public static final String INVALID_EVENT_START_DESC = " " + PREFIX_EVENT_START_LONG + "aaaa";
    // incorrect format
    public static final String INVALID_EVENT_END_DESC = " " + PREFIX_EVENT_END_LONG + "23:59 " + "25-12-31";
    // empty
    public static final String INVALID_EVENT_LOCATION_DESC = " " + PREFIX_EVENT_LOCATION_LONG + "";
    // * not allowed
    public static final String INVALID_EVENT_TAG_DESC = " " + PREFIX_EVENT_TAG_LONG + "hubby*";

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s event list.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased()
                < model.getEventManagerAndList().getFilteredItemsList().size());

        Event event = model.getEventManagerAndList().getFilteredItemsList()
                .get(targetIndex.getZeroBased());
        final String[] splitName = event.getName().value.split("\\s+");
        model.getEventManagerAndList()
                .updateFilteredItemsList(new NamePredicate(Operator.AND, List.of(splitName[0])));

        assertEquals(1, model.getEventManagerAndList().getFilteredItemsList().size());
    }
}
