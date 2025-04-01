package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.model.event.Attendance;
import seedu.address.model.event.Event;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setStartTime(event.getStartTime());
        descriptor.setEndTime(event.getEndTime());
        descriptor.setLocation(event.getLocation());
        descriptor.setTags(event.getTags());
        descriptor.setAttendance(event.getAttendance());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String eventName) {
        descriptor.setName(new Name(eventName));
        return this;
    }

    /**
     * Sets the {@code Datetime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventStartTime(String eventStartTime) {
        descriptor.setStartTime(new Datetime(eventStartTime));
        return this;
    }

    /**
     * Sets the {@code Datetime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventEndTime(String eventEndTime) {
        descriptor.setEndTime(new Datetime(eventEndTime));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withLocation(String eventLocation) {
        descriptor.setLocation(new Location(eventLocation));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code attendamce} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withAttendance(Attendance attendance) {
        descriptor.setAttendance(new Attendance(attendance));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
