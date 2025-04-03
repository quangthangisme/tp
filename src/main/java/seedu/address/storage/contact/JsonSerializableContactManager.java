package seedu.address.storage.contact;

import static seedu.address.storage.StorageMessage.MESSAGE_DUPLICATE_FOUND;
import static seedu.address.storage.StorageMessage.MESSAGE_INVALID_VALUE_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.item.ItemNotInvolvingContactManager;

/**
 * An Immutable contact list that is serializable to JSON format.
 */
@JsonRootName(value = "contactList")
public class JsonSerializableContactManager {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contacts list contains duplicate contact(s).";

    private static final Logger logger = LogsCenter.getLogger(JsonSerializableContactManager.class);

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableContactManager} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableContactManager(@JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code ItemManager<Contact>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableContactManager(ItemNotInvolvingContactManager<Contact> source) {
        contacts.addAll(source.getItemList().stream().map(JsonAdaptedContact::new).toList());
    }

    /**
     * Converts this address book into the model's {@code ContactManager} object.
     */
    public ContactManager toModelType() {
        ContactManager contactManager = new ContactManager();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact;
            try {
                contact = jsonAdaptedContact.toModelType();
            } catch (IllegalValueException e) {
                logger.info(MESSAGE_INVALID_VALUE_FOUND + e.getMessage());
                continue;
            }
            if (contactManager.hasItem(contact)) {
                logger.info(String.format(MESSAGE_DUPLICATE_FOUND, jsonAdaptedContact));
                continue;
            }
            contactManager.addItem(contact);
        }
        return contactManager;
    }

}
