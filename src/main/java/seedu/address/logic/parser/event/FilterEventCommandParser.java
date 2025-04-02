package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.read.FilterEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.predicate.EventContactPredicate;
import seedu.address.model.event.predicate.EventEndTimePredicate;
import seedu.address.model.event.predicate.EventPredicate;
import seedu.address.model.event.predicate.EventStartTimePredicate;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;

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
        NamePrefix namePrefix = new NamePrefix(FilterEventCommand.COMMAND_WORD);
        StartPrefix startPrefix = new StartPrefix(FilterEventCommand.COMMAND_WORD);
        EndPrefix endPrefix = new EndPrefix(FilterEventCommand.COMMAND_WORD);
        LocationPrefix locationPrefix = new LocationPrefix(FilterEventCommand.COMMAND_WORD);
        ContactPrefix contactPrefix = new ContactPrefix(FilterEventCommand.COMMAND_WORD);
        Prefix[] listOfPrefixes = Stream.of(
                namePrefix.getAll(),
                startPrefix.getAll(),
                endPrefix.getAll(),
                locationPrefix.getAll(),
                contactPrefix.getAll()
        ).flatMap(Arrays::stream).toArray(Prefix[]::new);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        EventPredicate predicate = new EventPredicate();

        if (namePrefix.getValue(argMultimap).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(namePrefix.getValue(argMultimap).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, namePrefix.toString()));
            }
            predicate.setNamePredicate(new NamePredicate(operatorStringPair.first(),
                List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (locationPrefix.getValue(argMultimap).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(locationPrefix.getValue(argMultimap).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, locationPrefix.toString()));
            }
            predicate.setLocationPredicate(new LocationPredicate(operatorStringPair.first(),
                List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (startPrefix.getValue(argMultimap).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(startPrefix.getValue(argMultimap).get());
            predicate.setStartTimePredicate(new EventStartTimePredicate(operatorStringPair.first(),
                ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        if (endPrefix.getValue(argMultimap).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(endPrefix.getValue(argMultimap).get());
            predicate.setEndTimePredicate(new EventEndTimePredicate(operatorStringPair.first(),
                ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        Optional<Pair<Operator, List<Index>>> contactFilterOpt = Optional.empty();
        if (contactPrefix.getValue(argMultimap).isPresent()) {
            Pair<Operator, String> operatorStringPair = ParserUtil.parseOperatorAndString(
                    contactPrefix.getValue(argMultimap).get());
            List<Index> contactIndices = ParserUtil.parseIndices(operatorStringPair.second());
            if (contactIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, contactPrefix.toString()));
            }
            contactFilterOpt = Optional.of(new Pair<>(operatorStringPair.first(), contactIndices));
            predicate.setContactPredicate(new EventContactPredicate(Operator.OR, List.of()));
        }

        if (!predicate.isAnyFieldNonNull()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterEventCommand(predicate, contactFilterOpt);
    }
}
