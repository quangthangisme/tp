package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;

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
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
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

    private static final PrefixAlias NAME_PREFIX = EventCliSyntax.PREFIX_ALIAS_EVENT_NAME;
    private static final PrefixAlias START_PREFIX = EventCliSyntax.PREFIX_ALIAS_EVENT_START;
    private static final PrefixAlias END_PREFIX = EventCliSyntax.PREFIX_ALIAS_EVENT_END;
    private static final PrefixAlias LOCATION_PREFIX = EventCliSyntax.PREFIX_ALIAS_EVENT_LOCATION;
    private static final PrefixAlias CONTACT_PREFIX = EventCliSyntax.PREFIX_ALIAS_EVENT_LINKED_CONTACT;
    private static final PrefixAlias TAG_PREFIX = EventCliSyntax.PREFIX_ALIAS_EVENT_TAG;

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
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(NAME_PREFIX, START_PREFIX, END_PREFIX, LOCATION_PREFIX, TAG_PREFIX, CONTACT_PREFIX)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterEventCommand.MESSAGE_USAGE));
        }

        return argMultimap;
    }

    private void setNamePredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(NAME_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(NAME_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), NAME_PREFIX.toString());

            predicate.setNamePredicate(new NamePredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }

    private void setLocationPredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(LOCATION_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(LOCATION_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), LOCATION_PREFIX.toString());

            predicate.setLocationPredicate(new LocationPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }

    private void setStartTimePredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(START_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(START_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), START_PREFIX.toString());

            predicate.setStartTimePredicate(new EventStartTimePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
    }

    private void setEndTimePredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(END_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(END_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), END_PREFIX.toString());

            predicate.setEndTimePredicate(new EventEndTimePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
    }

    private void setTagPredicate(ArgumentMultimap argMultimap, EventPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(TAG_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(TAG_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), TAG_PREFIX.toString());

            predicate.setTagPredicate(new TagPredicate(operatorStringPair.first(),
                    ParserUtil.parseTags(operatorStringPair.second())));
        }
    }

    private Optional<Pair<Operator, List<Index>>> parseContactPredicate(
            ArgumentMultimap argMultimap, EventPredicate predicate) throws ParseException {
        if (argMultimap.getValue(CONTACT_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(CONTACT_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), CONTACT_PREFIX.toString());

            List<Index> contactIndices = ParserUtil.parseIndices(operatorStringPair.second());

            if (contactIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, CONTACT_PREFIX));
            }

            predicate.setContactPredicate(new EventContactPredicate(Operator.OR, List.of()));

            return Optional.of(new Pair<>(operatorStringPair.first(), contactIndices));
        }

        return Optional.empty();
    }

    private void validateNonEmptyValue(String value, String prefix) throws ParseException {
        if (value.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_VALUES, prefix));
        }
    }
}
