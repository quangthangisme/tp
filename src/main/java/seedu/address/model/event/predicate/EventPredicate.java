package seedu.address.model.event.predicate;

import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.item.predicate.LocationPredicate;
import seedu.address.model.item.predicate.NamePredicate;
import seedu.address.model.item.predicate.TagPredicate;


/**
 * Represents a complex predicate for filtering Contact objects based on multiple criteria. Each
 * criterion consists of a column and associated filter conditions.
 */
public class EventPredicate implements Predicate<Event> {
    /**
     * Stores the details to filter the event with.
     */
    private NamePredicate namePredicate;
    private EventStartTimePredicate startTimePredicate;
    private EventEndTimePredicate endTimePredicate;
    private LocationPredicate locationPredicate;
    private TagPredicate tagPredicate;
    private EventContactPredicate contactPredicate;

    public EventPredicate() {
    }

    /**
     * Copy constructor. A defensive copy of {@code tags} is used internally.
     */
    public EventPredicate(EventPredicate toCopy) {
        setNamePredicate(toCopy.namePredicate);
        setStartTimePredicate(toCopy.startTimePredicate);
        setEndTimePredicate(toCopy.endTimePredicate);
        setLocationPredicate(toCopy.locationPredicate);
        setContactPredicate(toCopy.contactPredicate);
        setTagPredicate(toCopy.tagPredicate);
    }

    /**
     * Returns true if at least one field is non-null.
     */
    public boolean isAnyFieldNonNull() {
        return CollectionUtil.isAnyNonNull(namePredicate, startTimePredicate, endTimePredicate,
                locationPredicate, contactPredicate, tagPredicate);
    }

    public Optional<NamePredicate> getNamePredicate() {
        return Optional.ofNullable(namePredicate);
    }

    public void setNamePredicate(NamePredicate namePredicate) {
        this.namePredicate = namePredicate;
    }

    public Optional<EventStartTimePredicate> getStartTimePredicate() {
        return Optional.ofNullable(startTimePredicate);
    }

    public void setStartTimePredicate(EventStartTimePredicate startTimePredicate) {
        this.startTimePredicate = startTimePredicate;
    }

    public Optional<EventEndTimePredicate> getEndTimePredicate() {
        return Optional.ofNullable(endTimePredicate);
    }

    public void setEndTimePredicate(EventEndTimePredicate endTimePredicate) {
        this.endTimePredicate = endTimePredicate;
    }

    public Optional<LocationPredicate> getLocationPredicate() {
        return Optional.ofNullable(locationPredicate);
    }

    public void setLocationPredicate(LocationPredicate locationPredicate) {
        this.locationPredicate = locationPredicate;
    }

    public Optional<TagPredicate> getTagPredicate() {
        return Optional.ofNullable(tagPredicate);
    }

    public void setTagPredicate(TagPredicate tagPredicate) {
        this.tagPredicate = tagPredicate;
    }

    public Optional<EventContactPredicate> getContactPredicate() {
        return Optional.ofNullable(contactPredicate);
    }

    public void setContactPredicate(EventContactPredicate contactPredicate) {
        this.contactPredicate = contactPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("namePredicate", namePredicate)
                .add("startTimePredicate", startTimePredicate)
                .add("endTimePredicate", endTimePredicate)
                .add("locationPredicate", locationPredicate)
                .add("tagPredicate", tagPredicate)
                .add("contactPredicate", contactPredicate)
                .toString();
    }

    @Override
    public boolean test(Event event) {
        return (namePredicate == null || namePredicate.test(event))
                && (startTimePredicate == null || startTimePredicate.test(event))
                && (locationPredicate == null || locationPredicate.test(event))
                && (endTimePredicate == null || endTimePredicate.test(event))
                && (contactPredicate == null || contactPredicate.test(event))
                && (tagPredicate == null || tagPredicate.test(event));
    }
}
