package seedu.address.model.event;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;

/**
 * Represents the attendance of contacts in an event.
 * Stores a list of pairs where each pair contains a contact and their attendance status.
 */
public class Attendance {
    private final List<Pair<Contact, AttendanceStatus>> contacts;

    /**
     * Constructs an Attendance object with a list of contacts and their attendance status.
     */
    public Attendance(List<Pair<Contact, AttendanceStatus>> contacts) {
        this.contacts = List.copyOf(contacts);
    }

    /**
     * Constructs an empty Attendance object with no contacts.
     */
    public Attendance() {
        this.contacts = List.of();
    }

    /**
     * Constructs a copy of an existing Attendance object.
     */
    public Attendance(Attendance attendance) {
        this.contacts = List.copyOf(attendance.contacts);
    }

    /**
     * Returns the size of the attendance list.
     */
    public int size() {
        return contacts.size();
    }

    /**
     * Returns a copy of the list of contacts and their attendance statuses.
     */
    public List<Pair<Contact, AttendanceStatus>> getList() {
        return List.copyOf(contacts);
    }

    /**
     * Returns the pair at the specified index.
     */
    public Pair<Contact, AttendanceStatus> get(int index) {
        return contacts.get(index);
    }

    /**
     * Checks if a specific contact is in the attendance list.
     */
    public boolean contains(Contact contact) {
        return contacts.stream().anyMatch(pair -> pair.first().equals(contact));
    }

    /**
     * Finds invalid indices from a list of indices that are out of bounds of the current contacts list.
     */
    public List<Index> findInvalidIndices(List<Index> indices) {
        return indices.stream().filter(index -> index.getZeroBased() >= contacts.size())
                .toList();
    }

    /**
     * Adds a list of new contacts to the attendance list.
     * Ensures no duplicate contacts are added.
     */
    public Attendance add(List<Contact> newContacts) {
        assert newContacts.stream().noneMatch(this::contains);
        Stream<Pair<Contact, AttendanceStatus>> pairsToConcat =
                newContacts.stream().map(contact -> new Pair<>(contact, new AttendanceStatus(false)));
        return new Attendance(Stream.concat(contacts.stream(), pairsToConcat).toList());
    }

    /**
     * Removes contacts from the attendance list based on the provided indices.
     */
    public Attendance remove(List<Index> indices) {
        assert findInvalidIndices(indices).isEmpty();

        Set<Integer> indicesToRemove = indices.stream()
                .map(Index::getZeroBased)
                .collect(Collectors.toSet());

        List<Pair<Contact, AttendanceStatus>> updatedContacts = contacts.stream()
                .filter(pair -> !indicesToRemove.contains(contacts.indexOf(pair)))
                .collect(Collectors.toList());

        return new Attendance(updatedContacts);
    }

    /**
     * Removes contacts from the attendance list.
     */
    public Attendance remove(Contact contact) {
        assert contacts.stream().anyMatch(pair -> pair.first().equals(contact));

        List<Pair<Contact, AttendanceStatus>> updatedContacts = contacts.stream()
                .filter(pair -> !pair.first().equals(contact))
                .collect(Collectors.toList());

        return new Attendance(updatedContacts);
    }

    /**
     * Update contacts from the attendance list.
     */
    public Attendance set(Contact oldContact, Contact newContact) {
        assert contacts.stream().anyMatch(pair -> pair.first().equals(oldContact));

        List<Pair<Contact, AttendanceStatus>> updatedContacts = contacts.stream()
                .map(pair -> pair.first().equals(oldContact) ? new Pair<>(newContact,
                        pair.second()) : pair)
                .collect(Collectors.toList());

        return new Attendance(updatedContacts);
    }

    /**
     * Matches the given indices with the specified attendance status.
     *
     * @param indices List of indices to match.
     * @param status The attendance status to match against.
     * @return A list of indices where the attendance status matches the given status.
     */
    public List<Index> match(List<Index> indices, AttendanceStatus status) {
        assert findInvalidIndices(indices).isEmpty();
        return indices.stream().filter(index ->
                contacts.get(index.getZeroBased()).second().equals(status)).toList();
    }

    /**
     * Logs the attendance status for the specified indices.
     * The updated status must be different from the original status.
     */
    public Attendance log(List<Index> indices, AttendanceStatus status) {
        assert findInvalidIndices(indices).isEmpty();
        assert match(indices, status).isEmpty();

        List<Pair<Contact, AttendanceStatus>> updatedContacts = contacts.stream()
                .map(pair -> new Pair<>(pair.first(), pair.second()))
                .collect(Collectors.toList());

        for (Index index : indices) {
            int i = index.getZeroBased();
            updatedContacts.set(i, new Pair<>(contacts.get(i).first(), status));
        }

        return new Attendance(updatedContacts);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Attendance other)) {
            return false;
        }

        return contacts.equals(other.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contacts);
    }
}
