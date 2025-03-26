package seedu.address.logic.commands.todo;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.TodoMessages;
import seedu.address.logic.abstractcommand.FilterCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoLocationPredicate;
import seedu.address.model.todo.predicate.TodoNamePredicate;
import seedu.address.model.todo.predicate.TodoPersonPredicate;
import seedu.address.model.todo.predicate.TodoStatusPredicate;

/**
 * Filters and lists all events based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterTodoCommand extends FilterCommand<Todo> {

    public static final String COMMAND_WORD = "filter";

    private final TodoPredicate todoPredicate;

    /**
     * Creates a {@code FilterTodoCommand} to filter items that match the given predicates.
     */
    public FilterTodoCommand(TodoPredicate todoPredicate) {
        super(Model::getTodoManagerAndList);
        this.todoPredicate = todoPredicate;
    }


    @Override
    public Predicate<Todo> createPredicate(Model model) throws CommandException {
        return todoPredicate.getPredicate(model);
    }

    @Override
    public String getResultOverviewMessage(int numberOfResults) {
        return String.format(Messages.MESSAGE_SEARCH_OVERVIEW, numberOfResults);
    }

    /**
     * Stores the details to filter the todo with.
     */
    public static class TodoPredicate {
        private TodoNamePredicate namePredicate;
        private TodoDeadlinePredicate deadlinePredicate;
        private TodoLocationPredicate locationPredicate;
        private TodoStatusPredicate statusPredicate;
        private Operator personOperator;
        private List<Index> personIndices;

        public TodoPredicate() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public TodoPredicate(TodoPredicate toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setDeadlinePredicate(toCopy.deadlinePredicate);
            setLocationPredicate(toCopy.locationPredicate);
            setStatusPredicate(toCopy.statusPredicate);
            setPersonOperator(toCopy.personOperator);
            setPersonIndices(toCopy.personIndices);
        }

        /**
         * Returns true if at least one field is non-null.
         */
        public boolean isAnyFieldNonNull() {
            return CollectionUtil.isAnyNonNull(namePredicate, deadlinePredicate,
                    locationPredicate, statusPredicate, personIndices);
        }

        public void setNamePredicate(TodoNamePredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public Optional<TodoNamePredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setDeadlinePredicate(TodoDeadlinePredicate deadlinePredicate) {
            this.deadlinePredicate = deadlinePredicate;
        }

        public Optional<TodoDeadlinePredicate> getDeadlinePredicate() {
            return Optional.ofNullable(deadlinePredicate);
        }

        public void setLocationPredicate(TodoLocationPredicate locationPredicate) {
            this.locationPredicate = locationPredicate;
        }

        public Optional<TodoLocationPredicate> getLocationPredicate() {
            return Optional.ofNullable(locationPredicate);
        }

        public void setStatusPredicate(TodoStatusPredicate statusPredicate) {
            this.statusPredicate = statusPredicate;
        }

        public Optional<TodoStatusPredicate> getStatusPredicate() {
            return Optional.ofNullable(statusPredicate);
        }

        public Optional<Operator> getPersonOperator() {
            return Optional.ofNullable(personOperator);
        }

        public void setPersonOperator(Operator personOperator) {
            this.personOperator = personOperator;
        }

        public Optional<List<Index>> getPersonIndices() {
            return (personIndices != null) ? Optional.of(List.copyOf(personIndices))
                    : Optional.empty();
        }

        public void setPersonIndices(List<Index> personIndices) {
            this.personIndices = (personIndices != null) ? List.copyOf(personIndices) : null;
        }

        public Predicate<Todo> getPredicate(Model model) throws CommandException {
            Predicate<Todo> personPredicate;
            if (personIndices != null) {
                List<Person> filteredPersons = model.getPersonManagerAndList()
                        .getFilteredItemsList();

                for (Index index : personIndices) {
                    if (index.getZeroBased() >= filteredPersons.size()) {
                        throw new CommandException(String.format(
                                TodoMessages.MESSAGE_INVALID_LINKED_PERSON_INDEX, index.getOneBased()));
                    }
                }

                List<Person> persons = personIndices.stream()
                        .map(index -> filteredPersons.get(index.getZeroBased())).toList();

                personPredicate =
                        todo -> new TodoPersonPredicate(personOperator, persons).test(todo);
            } else {
                personPredicate = unused -> true;
            }

            return todo -> (namePredicate == null || namePredicate.test(todo))
                    && (deadlinePredicate == null || deadlinePredicate.test(todo))
                    && (locationPredicate == null || locationPredicate.test(todo))
                    && (statusPredicate == null || statusPredicate.test(todo))
                    && (personPredicate.test(todo));
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("namePredicate", namePredicate)
                    .add("deadlinePredicate", deadlinePredicate)
                    .add("locationPredicate", locationPredicate)
                    .add("statusPredicate", statusPredicate)
                    .add("personOperator", personOperator)
                    .add("personIndices", personIndices)
                    .toString();
        }

    }
}
