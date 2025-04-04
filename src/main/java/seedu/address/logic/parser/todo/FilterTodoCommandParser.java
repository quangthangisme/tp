package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.read.FilterTodoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.item.predicate.TagPredicate;
import seedu.address.model.todo.predicate.TodoContactPredicate;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoPredicate;
import seedu.address.model.todo.predicate.TodoStatusPredicate;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class FilterTodoCommandParser implements Parser<FilterTodoCommand> {

    private static final PrefixAlias NAME_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_NAME;
    private static final PrefixAlias DEADLINE_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_DEADLINE;
    private static final PrefixAlias LOCATION_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_LOCATION;
    private static final PrefixAlias STATUS_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_STATUS;
    private static final PrefixAlias CONTACT_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_LINKED_CONTACT;
    private static final PrefixAlias TAG_PREFIX = TodoCliSyntax.PREFIX_ALIAS_TODO_TAG;

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = tokenizeAndValidateArgs(args);
        TodoPredicate predicate = new TodoPredicate();

        setNamePredicate(argMultimap, predicate);
        setLocationPredicate(argMultimap, predicate);
        setStatusPredicate(argMultimap, predicate);
        setDeadlinePredicate(argMultimap, predicate);
        setTagPredicate(argMultimap, predicate);

        Optional<Pair<Operator, List<Index>>> contactFilterOpt =
                parseContactPredicate(argMultimap, predicate);

        if (!predicate.isAnyFieldNonNull()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterTodoCommand(predicate, contactFilterOpt);
    }

    private ArgumentMultimap tokenizeAndValidateArgs(String args) throws ParseException {
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(NAME_PREFIX, DEADLINE_PREFIX, LOCATION_PREFIX, STATUS_PREFIX, CONTACT_PREFIX)
                .toArray();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterTodoCommand.MESSAGE_USAGE));
        }

        return argMultimap;
    }

    private void setNamePredicate(ArgumentMultimap argMultimap, TodoPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(NAME_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(NAME_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), NAME_PREFIX);

            predicate.setNamePredicate(new NamePredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }

    private void setLocationPredicate(ArgumentMultimap argMultimap, TodoPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(LOCATION_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(LOCATION_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), LOCATION_PREFIX);

            predicate.setLocationPredicate(new LocationPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
    }

    private void setStatusPredicate(ArgumentMultimap argMultimap, TodoPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(STATUS_PREFIX).isPresent()) {
            String statusString = argMultimap.getValue(STATUS_PREFIX).get().trim();

            validateNonEmptyValue(statusString, STATUS_PREFIX);

            predicate.setStatusPredicate(new TodoStatusPredicate(ParserUtil.parseBoolean(statusString)));
        }
    }

    private void setDeadlinePredicate(ArgumentMultimap argMultimap, TodoPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(DEADLINE_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(DEADLINE_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), DEADLINE_PREFIX);

            predicate.setDeadlinePredicate(new TodoDeadlinePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
    }

    private void setTagPredicate(ArgumentMultimap argMultimap, TodoPredicate predicate)
            throws ParseException {
        if (argMultimap.getValue(TAG_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(TAG_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), TAG_PREFIX);

            predicate.setTagPredicate(new TagPredicate(operatorStringPair.first(),
                    ParserUtil.parseTags(operatorStringPair.second())));
        }
    }

    private Optional<Pair<Operator, List<Index>>> parseContactPredicate(
            ArgumentMultimap argMultimap, TodoPredicate predicate) throws ParseException {
        if (argMultimap.getValue(CONTACT_PREFIX).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(CONTACT_PREFIX).get());

            validateNonEmptyValue(operatorStringPair.second(), CONTACT_PREFIX);

            List<Index> contactIndices = ParserUtil.parseIndices(operatorStringPair.second());

            if (contactIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, CONTACT_PREFIX));
            }

            predicate.setContactPredicate(new TodoContactPredicate(Operator.OR, List.of()));

            return Optional.of(new Pair<>(operatorStringPair.first(), contactIndices));
        }

        return Optional.empty();
    }

    private void validateNonEmptyValue(String value, PrefixAlias prefix) throws ParseException {
        if (value.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_VALUES, prefix));
        }
    }
}
