package seedu.address.logic.parser.todo;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_LINKED_PERSON_INDEX;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS;

import java.util.List;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.todo.FilterTodoCommand;
import seedu.address.logic.commands.todo.FilterTodoCommand.TodoPredicate;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoLocationPredicate;
import seedu.address.model.todo.predicate.TodoNamePredicate;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TODO_NAME,
                PREFIX_TODO_DEADLINE, PREFIX_TODO_LOCATION, PREFIX_TODO_STATUS,
                PREFIX_LINKED_PERSON_INDEX);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TODO_NAME, PREFIX_TODO_DEADLINE,
                PREFIX_TODO_LOCATION, PREFIX_TODO_STATUS, PREFIX_LINKED_PERSON_INDEX);

        TodoPredicate predicate = new TodoPredicate();

        if (argMultimap.getValue(PREFIX_TODO_NAME).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_TODO_NAME).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_NAME));
            }
            predicate.setNamePredicate(new TodoNamePredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_TODO_LOCATION).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_TODO_LOCATION).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_LOCATION));
            }
            predicate.setLocationPredicate(new TodoLocationPredicate(operatorStringPair.first(),
                    List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_TODO_STATUS).isPresent()) {
            if (argMultimap.getValue(PREFIX_TODO_STATUS).get().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_TODO_STATUS));
            }
            predicate.setStatusPredicate(new TodoStatusPredicate(ParserUtil.parseBoolean(
                    argMultimap.getValue(PREFIX_TODO_STATUS).get()
            )));
        }
        if (argMultimap.getValue(PREFIX_TODO_DEADLINE).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                    ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_TODO_DEADLINE).get());
            predicate.setDeadlinePredicate(new TodoDeadlinePredicate(operatorStringPair.first(),
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

        return new FilterTodoCommand(predicate);
    }
}
