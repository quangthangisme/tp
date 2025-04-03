package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.event.AttendanceStatus;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.storage.DummyContactBuilder;

/**
 * Jackson-friendly version of {@code Pair<Contact, Boolean>}.
 */
public class JsonAdaptedAttendancePair {
    public static final String INVALID_ID_MESSAGE = "ID %s is not found in contact list!";

    public final String id;
    public final String status;

    /**
     * Constructs a {@code JsonAdaptedAttendancePair}.
     */
    @JsonCreator
    public JsonAdaptedAttendancePair(
            @JsonProperty("contact") String id,
            @JsonProperty("status") String status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Converts a given {@code Pair} into this class for Jackson use.
     */
    public JsonAdaptedAttendancePair(Pair<Contact, AttendanceStatus> source) {
        id = source.first().getId().fullId;
        status = String.valueOf(source.second().hasAttended());
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Pair} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               tag.
     */
    public Pair<Contact, AttendanceStatus> toModelType(
            ItemNotInvolvingContactManager<Contact> contactManager
    ) throws IllegalValueException {
        Id contactId;
        try {
            contactId = new Id(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        Contact contact;
        try {
            Contact dummyContact = DummyContactBuilder.build(contactId);
            contact = contactManager.getUpdateItem(dummyContact);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(String.format(INVALID_ID_MESSAGE, id));
        }

        if (!"true".equals(status) && !"false".equals(status)) {
            throw new IllegalValueException("Status is not \"true\" or \"false\"!");
        }
        return new Pair<>(contact, new AttendanceStatus(Boolean.parseBoolean(status)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("id", id)
                .add("status", status).toString();
    }
}
