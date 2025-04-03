package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_LONG;

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
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.todo.predicate.TodoContactPredicate;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoPredicate;
import seedu.address.model.todo.predicate.TodoStatusPredicate;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class FilterTodoCommandParser implements Parser<FilterTodoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterTodoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME_LONG,
                PREFIX_TODO_DEADLINE_LONG, PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_STATUS_LONG,
                PREFIX_TODO_LINKED_CONTACT_LONG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME_LONG, PREFIX_TODO_DEADLINE_LONG,
                PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_STATUS_LONG, PREFIX_TODO_LINKED_CONTACT_LONG);

        TodoPredicate predicate = new TodoPredicate();

        if (argMultimap.getValue(PREFIX_TODO_NAME_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_TODO_NAME_LONG).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_NAME_LONG));
            }
            predicate.setNamePredicate(new NamePredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_TODO_LOCATION_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_TODO_LOCATION_LONG).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_LOCATION_LONG));
            }
            predicate.setLocationPredicate(new LocationPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_TODO_STATUS_LONG).isPresent()) {
            String statusString = argMultimap.getValue(PREFIX_TODO_STATUS_LONG).get().trim();
            if (statusString.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_STATUS_LONG));
            }
            predicate.setStatusPredicate(new TodoStatusPredicate(ParserUtil.parseBoolean(statusString)));
        }
        if (argMultimap.getValue(PREFIX_TODO_DEADLINE_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_TODO_DEADLINE_LONG).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_DEADLINE_LONG));
            }
            predicate.setDeadlinePredicate(new TodoDeadlinePredicate(operatorStringPair.first(),
                    ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        Optional<Pair<Operator, List<Index>>> contactFilterOpt = Optional.empty();
        if (argMultimap.getValue(PREFIX_TODO_LINKED_CONTACT_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair = ParserUtil.parseOperatorAndString(
                    argMultimap.getValue(PREFIX_TODO_LINKED_CONTACT_LONG).get());
            String contactString = operatorStringPair.second().trim();
            if (contactString.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_LINKED_CONTACT_LONG));
            }
            List<Index> contactIndices = ParserUtil.parseIndices(contactString);
            if (contactIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES,
                        PREFIX_TODO_LINKED_CONTACT_LONG));
            }
            contactFilterOpt = Optional.of(new Pair<>(operatorStringPair.first(), contactIndices));
            predicate.setContactPredicate(new TodoContactPredicate(Operator.OR, List.of()));
        }

        if (!predicate.isAnyFieldNonNull()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterTodoCommand(predicate, contactFilterOpt);
    }
}
