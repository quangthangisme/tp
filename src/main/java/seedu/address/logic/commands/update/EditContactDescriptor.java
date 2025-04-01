package seedu.address.logic.commands.update;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Course;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Group;
import seedu.address.model.contact.Id;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * Stores the details to edit the contact with. Each non-empty field value will replace the
 * corresponding field value of the contact.
 */
public class EditContactDescriptor {
    private Id id;
    private Name name;
    private Email email;
    private Course course;
    private Group group;
    private Set<Tag> tags;

    public EditContactDescriptor() {
    }

    /**
     * Copy constructor. A defensive copy of {@code tags} is used internally.
     */
    public EditContactDescriptor(EditContactDescriptor toCopy) {
        setId(toCopy.id);
        setName(toCopy.name);
        setEmail(toCopy.email);
        setTags(toCopy.tags);
        setCourse(toCopy.course);
        setGroup(toCopy.group);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(id, name, email, tags, course, group);
    }

    public Optional<Id> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Optional<Course> getCourse() {
        return Optional.ofNullable(course);
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Optional<Group> getGroup() {
        return Optional.ofNullable(group);
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if
     * modification is attempted. Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used
     * internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactDescriptor otherEditContactDescriptor)) {
            return false;
        }

        return Objects.equals(id, otherEditContactDescriptor.id)
                && Objects.equals(name, otherEditContactDescriptor.name)
                && Objects.equals(email, otherEditContactDescriptor.email)
                && Objects.equals(course, otherEditContactDescriptor.course)
                && Objects.equals(group, otherEditContactDescriptor.group)
                && Objects.equals(tags, otherEditContactDescriptor.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("name", name)
                .add("email", email)
                .add("course", course)
                .add("group", group)
                .add("tags", tags)
                .toString();
    }

}
