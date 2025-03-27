package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_LINKED_PERSON_INDEX;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_START_DATETIME;

import java.util.List;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.FilterEventCommand;
import seedu.address.logic.commands.event.FilterEventCommand.EventPredicate;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.predicate.EventEndTimePredicate;
import seedu.address.model.event.predicate.EventLocationPredicate;
import seedu.address.model.event.predicate.EventNamePredicate;
import seedu.address.model.event.predicate.EventStartTimePredicate;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class FilterEventCommandParser implements Parser<FilterEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_EVENT_LOCATION,
                PREFIX_LINKED_PERSON_INDEX);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME, PREFIX_START_DATETIME,
                PREFIX_END_DATETIME, PREFIX_EVENT_LOCATION, PREFIX_LINKED_PERSON_INDEX);

        EventPredicate predicate = new EventPredicate();

        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_NAME).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_EVENT_NAME));
            }
            predicate.setNamePredicate(new EventNamePredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_EVENT_LOCATION).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_LOCATION).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_EVENT_LOCATION));
            }
            predicate.setLocationPredicate(new EventLocationPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_START_DATETIME).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_START_DATETIME).get());
            predicate.setStartTimePredicate(new EventStartTimePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        if (argMultimap.getValue(PREFIX_END_DATETIME).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_END_DATETIME).get());
            predicate.setEndTimePredicate(new EventEndTimePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        if (argMultimap.getValue(PREFIX_LINKED_PERSON_INDEX).isPresent()) {
            Pair<Operator, String> operatorStringPair = ParserUtil.parseOperatorAndString(
                    argMultimap.getValue(PREFIX_LINKED_PERSON_INDEX).get());
            predicate.setPersonOperator(operatorStringPair.first());
            List<Index> personIndices = ParserUtil.parseIndices(operatorStringPair.second());
            if (personIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES,
                        PREFIX_LINKED_PERSON_INDEX));
            }
            predicate.setPersonIndices(ParserUtil.parseIndices(operatorStringPair.second()));
        }

        if (!predicate.isAnyFieldNonNull()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterEventCommand(predicate);
    }
}
