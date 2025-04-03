package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.event.AttendanceStatus;
import seedu.address.model.item.ItemNotInvolvingContactManager;
import seedu.address.storage.DummyContactBuilder;

/**
 * Jackson-friendly version of {@code Pair<Contact, Boolean>}.
 */
public class JsonAdaptedAttendancePair {
    public static final String INVALID_ID_MESSAGE = "ID %s is invalid!";

    private final String id;
    private final boolean status;

    /**
     * Constructs a {@code JsonAdaptedAttendancePair}.
     */
    @JsonCreator
    public JsonAdaptedAttendancePair(
            @JsonProperty("contact") String id,
            @JsonProperty("status") boolean status) {
        this.id = id;
        this.status = status;
    }

    /**
     * Converts a given {@code Pair} into this class for Jackson use.
     */
    public JsonAdaptedAttendancePair(Pair<Contact, AttendanceStatus> source) {
        id = source.first().getId().fullId;
        status = source.second().hasAttended();
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
        Contact contact;
        try {
            Contact dummyContact = DummyContactBuilder.build(new Id(id));
            contact = contactManager.getUpdateItem(dummyContact);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(String.format(INVALID_ID_MESSAGE, id));
        }
        return new Pair<>(contact, new AttendanceStatus(status));
    }
}
