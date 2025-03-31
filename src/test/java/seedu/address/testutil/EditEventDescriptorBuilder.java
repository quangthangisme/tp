package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Tag;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventName;

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
        descriptor.setContacts(event.getContacts());
        descriptor.setMarkedList(event.getMarkedList());
    }

    /**
     * Sets the {@code EventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String eventName) {
        descriptor.setName(new EventName(eventName));
        return this;
    }

    /**
     * Sets the {@code EventDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventStartTime(String eventStartTime) {
        descriptor.setStartTime(new EventDateTime(eventStartTime));
        return this;
    }

    /**
     * Sets the {@code EventDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventEndTime(String eventEndTime) {
        descriptor.setEndTime(new EventDateTime(eventEndTime));
        return this;
    }

    /**
     * Sets the {@code EventLocation} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventLocation(String eventLocation) {
        descriptor.setLocation(new EventLocation(eventLocation));
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
     * Sets the {@code contacts} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withContacts(Contact... contacts) {
        List<Contact> contactSet = List.of(contacts);
        descriptor.setContacts(contactSet);
        return this;
    }

    /**
     * Sets the {@code markedList} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withMarkedList(Boolean... markedList) {
        List<Boolean> markedListList = Arrays.stream(markedList).collect(Collectors.toList());
        descriptor.setMarkedList(markedListList);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
