package seedu.address.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.update.EditTodoDescriptor;
import seedu.address.model.contact.Contact;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoStatus;

/**
 * A utility class to help with building EditTodoDescriptor objects.
 */
public class EditTodoDescriptorBuilder {

    private EditTodoDescriptor descriptor;

    public EditTodoDescriptorBuilder() {
        descriptor = new EditTodoDescriptor();
    }

    public EditTodoDescriptorBuilder(EditTodoDescriptor descriptor) {
        this.descriptor = new EditTodoDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTodoDescriptor} with fields containing {@code todo}'s details
     */
    public EditTodoDescriptorBuilder(Todo todo) {
        descriptor = new EditTodoDescriptor();
        descriptor.setName(todo.getName());
        descriptor.setLocation(todo.getLocation());
        descriptor.setDeadline(todo.getDeadline());
        descriptor.setStatus(todo.getStatus());
        descriptor.setContacts(todo.getContacts());
        descriptor.setTags(todo.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTodoDescriptor} that we are building.
     */
    public EditTodoDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditTodoDescriptor} that we are building.
     */
    public EditTodoDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code EditTodoDescriptor} that we are building.
     */
    public EditTodoDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Datetime(deadline));
        return this;
    }

    /**
     * Sets the {@code status} of the {@code EditTodoDescriptor} that we are building.
     */
    public EditTodoDescriptorBuilder withStatus(String status) {
        descriptor.setStatus(new TodoStatus(Boolean.parseBoolean(status.toLowerCase().trim())));
        return this;
    }

    /**
     * Sets the {@code contacts} of the {@code EditTodoDescriptor} that we are building.
     */
    public EditTodoDescriptorBuilder withContacts(Contact... contacts) {
        descriptor.setContacts(List.of(contacts));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTodoDescriptor}
     * that we are building.
     */
    public EditTodoDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTodoDescriptor build() {
        return descriptor;
    }
}

