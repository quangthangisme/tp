package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_OPERATOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.logic.commands.person.FindPersonCommand;
import seedu.address.model.person.Column;
import seedu.address.model.person.FilterCriteria;
import seedu.address.model.person.Operator;
import seedu.address.model.person.PersonPredicate;

public class FilterPersonCommandParserTest {

    private final FilterPersonCommandParser parser = new FilterPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // Single value with default AND operator
        Map<Column, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Collections.singletonList("alice")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/alice", expectedFilterCommand);

        // Multiple values with explicit OR operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.OR, Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/or: alice bob", expectedFilterCommand);

        // Multiple columns
        expectedPredicateMap.clear();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Collections.singletonList("alice")));
        expectedPredicateMap.put(Column.EMAIL, new FilterCriteria(Operator.AND, Collections.singletonList("gmail")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/alice e/gmail", expectedFilterCommand);
    }

    @Test
    public void parse_quotedValues_returnsFilterCommand() {
        // Quote-wrapped values
        Map<Column, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("alice pauline")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/\"alice pauline\"", expectedFilterCommand);

        // Mix of quoted and unquoted values
        expectedPredicateMap.clear();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.OR, Arrays.asList("alice pauline", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/or: \"alice pauline\" bob", expectedFilterCommand);
    }

    @Test
    public void parse_allOperators_returnsFilterCommand() {
        // AND operator
        Map<Column, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Arrays.asList("alice", "pauline")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/and: alice pauline", expectedFilterCommand);

        // OR operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.OR, Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/or: alice bob", expectedFilterCommand);

        // NAND operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.NAND, Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/nand: alice bob", expectedFilterCommand);

        // NOR operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/nor: alice bob", expectedFilterCommand);
    }

    @Test
    public void parse_invalidOperator_throwsParseException() {
        assertParseFailure(parser, " n/invalid: alice",
                String.format(MESSAGE_UNRECOGNIZED_OPERATOR, "invalid")
        );
    }

    @Test
    public void parse_multipleFilters_returnsFilterCommand() {
        // Test combining multiple filters with different operators
        Map<Column, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, List.of("alice")));
        expectedPredicateMap.put(Column.EMAIL, new FilterCriteria(Operator.OR, Arrays.asList("gmail", "yahoo")));
        expectedPredicateMap.put(Column.TAG, new FilterCriteria(Operator.NAND, List.of("friend")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " n/alice e/or: gmail yahoo t/nand: friend", expectedFilterCommand);
    }
}
