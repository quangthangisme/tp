package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.AttendanceStatus;
import seedu.address.storage.contact.JsonAdaptedContact;

/**
 * Jackson-friendly version of {@code Pair<Contact, Boolean>}.
 */
public class JsonAdaptedAttendancePair {

    private final JsonAdaptedContact contact;
    private final boolean status;

    /**
     * Constructs a {@code JsonAdaptedAttendancePair}.
     */
    @JsonCreator
    public JsonAdaptedAttendancePair(
            @JsonProperty("contact") JsonAdaptedContact contact,
            @JsonProperty("status") boolean status) {
        this.contact = contact;
        this.status = status;
    }

    /**
     * Converts a given {@code Pair} into this class for Jackson use.
     */
    public JsonAdaptedAttendancePair(Pair<Contact, AttendanceStatus> source) {
        contact = new JsonAdaptedContact(source.first());
        status = source.second().hasAttended();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Pair} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               tag.
     */
    public Pair<Contact, AttendanceStatus> toModelType() throws IllegalValueException {
        return new Pair<>(contact.toModelType(), new AttendanceStatus(status));
    }
}
