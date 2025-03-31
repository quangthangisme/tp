package seedu.address.model.todo;

import static seedu.address.logic.Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.todo.predicate.TodoContactPredicate;
import seedu.address.model.todo.predicate.TodoDeadlinePredicate;
import seedu.address.model.todo.predicate.TodoStatusPredicate;


/**
 * Represents a complex predicate for filtering Contact objects based on multiple criteria.
 * Each criterion consists of a column and associated filter conditions.
 */
public class TodoPredicate implements Predicate<Todo> {
    /**
     * Stores the details to filter the todo with.
     */
    private NamePredicate namePredicate;
    private TodoDeadlinePredicate deadlinePredicate;
    private LocationPredicate locationPredicate;
    private TodoStatusPredicate statusPredicate;
    private Operator contactOperator;
    private List<Index> contactIndices;

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
        setContactOperator(toCopy.contactOperator);
        setContactIndices(toCopy.contactIndices);
    }

    /**
     * Returns true if at least one field is non-null.
     */
    public boolean isAnyFieldNonNull() {
        return CollectionUtil.isAnyNonNull(namePredicate, deadlinePredicate,
                locationPredicate, statusPredicate, contactIndices);
    }

    public Optional<NamePredicate> getNamePredicate() {
        return Optional.ofNullable(namePredicate);
    }

    public void setNamePredicate(NamePredicate namePredicate) {
        this.namePredicate = namePredicate;
    }

    public Optional<TodoDeadlinePredicate> getDeadlinePredicate() {
        return Optional.ofNullable(deadlinePredicate);
    }

    public void setDeadlinePredicate(TodoDeadlinePredicate deadlinePredicate) {
        this.deadlinePredicate = deadlinePredicate;
    }

    public Optional<LocationPredicate> getLocationPredicate() {
        return Optional.ofNullable(locationPredicate);
    }

    public void setLocationPredicate(LocationPredicate locationPredicate) {
        this.locationPredicate = locationPredicate;
    }

    public Optional<TodoStatusPredicate> getStatusPredicate() {
        return Optional.ofNullable(statusPredicate);
    }

    public void setStatusPredicate(TodoStatusPredicate statusPredicate) {
        this.statusPredicate = statusPredicate;
    }

    public Optional<Operator> getContactOperator() {
        return Optional.ofNullable(contactOperator);
    }

    public void setContactOperator(Operator contactOperator) {
        this.contactOperator = contactOperator;
    }

    public Optional<List<Index>> getContactIndices() {
        return (contactIndices != null) ? Optional.of(List.copyOf(contactIndices))
                : Optional.empty();
    }

    public void setContactIndices(List<Index> contactIndices) {
        this.contactIndices = (contactIndices != null) ? List.copyOf(contactIndices) : null;
    }

    public Predicate<Todo> getPredicate(Model model) throws CommandException {
        Predicate<Todo> contactPredicate;
        if (contactIndices != null) {
            List<Contact> filteredContacts = model.getContactManagerAndList()
                    .getFilteredItemsList();

            for (Index index : contactIndices) {
                if (index.getZeroBased() >= filteredContacts.size()) {
                    throw new CommandException(String.format(
                            MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX, index.getOneBased()));
                }
            }

            List<Contact> contacts = contactIndices.stream()
                    .map(index -> filteredContacts.get(index.getZeroBased())).toList();

            contactPredicate =
                    todo -> new TodoContactPredicate(contactOperator, contacts).test(todo);
        } else {
            contactPredicate = unused -> true;
        }

        return todo -> (namePredicate == null || namePredicate.test(todo))
                && (deadlinePredicate == null || deadlinePredicate.test(todo))
                && (locationPredicate == null || locationPredicate.test(todo))
                && (statusPredicate == null || statusPredicate.test(todo))
                && (contactPredicate.test(todo));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("deadlinePredicate", deadlinePredicate)
                .add("locationPredicate", locationPredicate)
                .add("statusPredicate", statusPredicate)
                .add("contactOperator", contactOperator)
                .add("contactIndices", contactIndices)
                .toString();
    }

    @Override
    public boolean test(Todo todo) {
        return false;
    }
}
