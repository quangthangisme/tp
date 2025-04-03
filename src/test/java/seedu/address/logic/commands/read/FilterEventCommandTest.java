package seedu.address.logic.commands.read;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_SEARCH_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.CRYING;
import static seedu.address.testutil.TypicalEvents.CRYING_MULTIPLE_TAG;
import static seedu.address.testutil.TypicalEvents.CRYING_WITH_TAG;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.STUFF_EVENT;
import static seedu.address.testutil.TypicalEvents.STUFF_EVENT_2;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.event.predicate.EventEndTimePredicate;
import seedu.address.model.event.predicate.EventPredicate;
import seedu.address.model.event.predicate.EventStartTimePredicate;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.item.predicate.DatetimePredicate;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.item.predicate.TagPredicate;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterEventCommand}.
 */
public class FilterEventCommandTest {
    private final Model model = new ModelManager(new UserPrefs(), new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(), new EventManagerWithFilteredList(getTypicalEventList()));
    private final Model expectedModel = new ModelManager(new UserPrefs(), new ContactManagerWithFilteredList(),
            new TodoManagerWithFilteredList(), new EventManagerWithFilteredList(getTypicalEventList()));

    @Test
    public void execute_nameFilterAnd() {
        EventPredicate predicate = new EventPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.AND, Arrays.asList("stuff", "stuff2")));
        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterOr() {
        EventPredicate predicate = new EventPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.OR, Arrays.asList("stuff", "stuff2")));
        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(STUFF_EVENT, STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterNand() {
        EventPredicate predicate = new EventPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.NAND, Arrays.asList("stuff", "stuff2")));
        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING, STUFF_EVENT);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterNor() {
        EventPredicate predicate = new EventPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.NOR, Arrays.asList("stuff", "stuff2")));
        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_nameFilterPartialMatch() {
        EventPredicate predicate = new EventPredicate();
        predicate.setNamePredicate(new NamePredicate(Operator.OR, Arrays.asList("tag")));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING_MULTIPLE_TAG, CRYING_WITH_TAG);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_startDatetimeAndFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setStartTimePredicate(new EventStartTimePredicate(Operator.AND,
                Arrays.asList(new DatetimePredicate("[25-12-31 23:59/-]"),
                        new DatetimePredicate("[-/26-01-01 00:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_startDatetimeOrFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setStartTimePredicate(new EventStartTimePredicate(Operator.OR,
                Arrays.asList(new DatetimePredicate("[25-12-31 23:59/-]"),
                        new DatetimePredicate("[-/25-12-31 23:58]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING, STUFF_EVENT,
                STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_startDatetimeNandFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setStartTimePredicate(new EventStartTimePredicate(Operator.NAND,
                Arrays.asList(new DatetimePredicate("[25-12-31 23:59/-]"),
                        new DatetimePredicate("[-/26-01-01 00:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(STUFF_EVENT, STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_startDatetimeNorFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setStartTimePredicate(new EventStartTimePredicate(Operator.NOR,
                Arrays.asList(new DatetimePredicate("[25-12-31 23:59/-]"),
                        new DatetimePredicate("[-/26-01-01 00:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of();

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_endDatetimeAndFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setEndTimePredicate(new EventEndTimePredicate(Operator.AND,
                Arrays.asList(new DatetimePredicate("[26-01-01 00:59/-]"),
                        new DatetimePredicate("[-/26-01-01 00:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_endDatetimeOrFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setEndTimePredicate(new EventEndTimePredicate(Operator.OR,
                Arrays.asList(new DatetimePredicate("[28-01-01 00:59/-]"),
                        new DatetimePredicate("[-/26-01-01 23:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING, STUFF_EVENT,
                STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_endDatetimeNandFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setEndTimePredicate(new EventEndTimePredicate(Operator.NAND,
                Arrays.asList(new DatetimePredicate("[26-01-01 00:59/-]"),
                        new DatetimePredicate("[-/26-01-01 00:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(STUFF_EVENT, STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_endDatetimeNorFilter() {
        EventPredicate predicate = new EventPredicate();
        predicate.setEndTimePredicate(new EventEndTimePredicate(Operator.NOR,
                Arrays.asList(new DatetimePredicate("[28-01-01 00:59/-]"),
                        new DatetimePredicate("[-/26-01-01 23:59]"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of();

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_locationFilterAnd() {
        EventPredicate predicate = new EventPredicate();
        predicate.setLocationPredicate(new LocationPredicate(Operator.AND, List.of("prof", "office")));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_locationFilterOr() {
        EventPredicate predicate = new EventPredicate();
        predicate.setLocationPredicate(new LocationPredicate(Operator.OR, List.of("NUS", "office")));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING, STUFF_EVENT,
                STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_locationFilterNor() {
        EventPredicate predicate = new EventPredicate();
        predicate.setLocationPredicate(new LocationPredicate(Operator.NOR, List.of("NUS", "office")));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of();

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_locationFilterNand() {
        EventPredicate predicate = new EventPredicate();
        predicate.setLocationPredicate(new LocationPredicate(Operator.NAND, List.of("NUS", "office")));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_MULTIPLE_TAG, CRYING_WITH_TAG, MEETING, STUFF_EVENT,
                STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }


    @Test
    public void execute_tagFilterAnd() {
        EventPredicate predicate = new EventPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.AND, Set.of(new Tag("sad"), new Tag("you"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());
        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING_MULTIPLE_TAG);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_tagFilterOr() {
        EventPredicate predicate = new EventPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.OR, Set.of(new Tag("sad"), new Tag("you"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING_MULTIPLE_TAG, CRYING_WITH_TAG);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }


    @Test
    public void execute_tagFilterNor() {
        EventPredicate predicate = new EventPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.NOR, Set.of(new Tag("sad"), new Tag("you"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, MEETING, STUFF_EVENT, STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }

    public void execute_tagFilterNand() {
        EventPredicate predicate = new EventPredicate();
        predicate.setTagPredicate(new TagPredicate(Operator.NAND, Set.of(new Tag("sad"), new Tag("you"))));

        FilterEventCommand command = new FilterEventCommand(predicate, Optional.empty());

        expectedModel.getEventManagerAndList().updateFilteredItemsList(predicate);
        List<Event> expectedList = List.of(CRYING, CRYING_WITH_TAG, MEETING, STUFF_EVENT, STUFF_EVENT_2);

        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, expectedList.size());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedList, model.getEventManagerAndList().getFilteredItemsList());
    }


}
