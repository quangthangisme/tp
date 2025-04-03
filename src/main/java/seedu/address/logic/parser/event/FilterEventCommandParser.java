package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.List;
import java.util.Optional;

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
import seedu.address.model.item.predicate.TagPredicate;

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

        ArgumentMultimap argMultimap = tokenizeAndValidateArgs(args);
        EventPredicate predicate = new EventPredicate();

        setNamePredicate(argMultimap, predicate);
        setLocationPredicate(argMultimap, predicate);
        setStartTimePredicate(argMultimap, predicate);
        setEndTimePredicate(argMultimap, predicate);
        setTagPredicate(argMultimap, predicate);

        Optional<Pair<Operator, List<Index>>> contactFilterOpt =
                parseContactPredicate(argMultimap, predicate);

        if (!predicate.isAnyFieldNonNull()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterEventCommand(predicate, contactFilterOpt);
    }

    private ArgumentMultimap tokenizeAndValidateArgs(String args) throws ParseException {
        List<Prefix> allPrefixes = List.of(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG, PREFIX_EVENT_END_LONG,
                PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_TAG_LONG, PREFIX_EVENT_LINKED_CONTACT_LONG);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, allPrefixes.toArray(new Prefix[0]));
        argMultimap.verifyNoDuplicatePrefixesFor(allPrefixes.toArray(new Prefix[0]));

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterEventCommand.MESSAGE_USAGE));
        }

        return argMultimap;
    }

    private void setNamePredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_NAME_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_NAME_LONG).get());

            validateNonEmptyValue(operatorStringPair.second(), PREFIX_EVENT_NAME_LONG);

            predicate.setNamePredicate(new NamePredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }

    private void setLocationPredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).get());

            validateNonEmptyValue(operatorStringPair.second(), PREFIX_EVENT_LOCATION_LONG);

            predicate.setLocationPredicate(new LocationPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }

    private void setStartTimePredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_START_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_START_LONG).get());

            validateNonEmptyValue(operatorStringPair.second(), PREFIX_EVENT_START_LONG);

            predicate.setStartTimePredicate(new EventStartTimePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
    }

    private void setEndTimePredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_END_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_END_LONG).get());

            validateNonEmptyValue(operatorStringPair.second(), PREFIX_EVENT_END_LONG);

            predicate.setEndTimePredicate(new EventEndTimePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
    }

    private void setTagPredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_TAG_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_TAG_LONG).get());

            validateNonEmptyValue(operatorStringPair.second(), PREFIX_EVENT_TAG_LONG);

            predicate.setTagPredicate(new TagPredicate(operatorStringPair.first(),
                    ParserUtil.parseTags(operatorStringPair.second())));
        }
    }

    private Optional<Pair<Operator, List<Index>>> parseContactPredicate(
            ArgumentMultimap argMultimap, EventPredicate predicate) throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).get());

            validateNonEmptyValue(operatorStringPair.second(), PREFIX_EVENT_LINKED_CONTACT_LONG);

            List<Index> contactIndices = ParserUtil.parseIndices(operatorStringPair.second());

            if (contactIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_EVENT_LINKED_CONTACT_LONG));
            }

            predicate.setContactPredicate(new EventContactPredicate(Operator.OR, List.of()));

            return Optional.of(new Pair<>(operatorStringPair.first(), contactIndices));
        }

        return Optional.empty();
    }

    private void validateNonEmptyValue(String value, Prefix prefix) throws ParseException {
        if (value.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_VALUES, prefix));
        }
    }
}
