package seedu.address.model.todo.predicate;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.item.predicate.TagPredicate;
import seedu.address.model.todo.Todo;


/**
 * Represents a complex predicate for filtering Contact objects based on multiple criteria. Each
 * criterion consists of a column and associated filter conditions.
 */
public class TodoPredicate implements Predicate<Todo> {
    /**
     * Stores the details to filter the todo with.
     */
    private NamePredicate namePredicate;
    private TodoDeadlinePredicate deadlinePredicate;
    private LocationPredicate locationPredicate;
    private TodoStatusPredicate statusPredicate;
    private TagPredicate tagPredicate;
    private TodoContactPredicate contactPredicate;

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
        setContactPredicate(toCopy.contactPredicate);
        setTagPredicate(toCopy.tagPredicate);
    }

    /**
     * Returns true if at least one field is non-null.
     */
    public boolean isAnyFieldNonNull() {
        return CollectionUtil.isAnyNonNull(namePredicate, deadlinePredicate, tagPredicate,
                locationPredicate, statusPredicate, contactPredicate);
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

    public Optional<TagPredicate> getTagPredicate() {
        return Optional.ofNullable(tagPredicate);
    }

    public void setTagPredicate(TagPredicate tagPredicate) {
        this.tagPredicate = tagPredicate;
    }

    public Optional<TodoContactPredicate> getContactPredicate() {
        return Optional.ofNullable(contactPredicate);
    }

    public void setContactPredicate(TodoContactPredicate contactPredicate) {
        this.contactPredicate = contactPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("deadlinePredicate", deadlinePredicate)
                .add("locationPredicate", locationPredicate)
                .add("statusPredicate", statusPredicate)
                .add("tagPredicate", tagPredicate)
                .add("contactPredicate", contactPredicate)
                .toString();
    }

    @Override
    public boolean test(Todo todo) {
        return (namePredicate == null || namePredicate.test(todo))
                && (deadlinePredicate == null || deadlinePredicate.test(todo))
                && (locationPredicate == null || locationPredicate.test(todo))
                && (statusPredicate == null || statusPredicate.test(todo))
                && (contactPredicate == null || contactPredicate.test(todo));
    }
}
