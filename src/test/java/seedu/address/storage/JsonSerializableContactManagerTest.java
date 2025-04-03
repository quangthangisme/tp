package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.contact.ContactManager;
import seedu.address.storage.contact.JsonSerializableContactManager;
import seedu.address.testutil.TypicalContacts;

public class JsonSerializableContactManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_CONTACTS_FILE = TEST_DATA_FOLDER.resolve(
            "typicalContactsAddressBook.json");
    private static final Path INVALID_CONTACT_FILE = TEST_DATA_FOLDER.resolve(
            "invalidContactAddressBook.json");
    private static final Path DUPLICATE_CONTACT_FILE = TEST_DATA_FOLDER.resolve(
            "duplicateContactAddressBook.json");

    @Test
    public void toModelType_typicalContactsFile_success() throws Exception {
        JsonSerializableContactManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_CONTACTS_FILE,
                JsonSerializableContactManager.class).get();
        ContactManager contactManagerFromFile = dataFromFile.toModelType();
        ContactManager typicalContactsContactManager = TypicalContacts.getTypicalAddressBook();
        assertEquals(contactManagerFromFile, typicalContactsContactManager);
    }

    @Test
    public void toModelType_invalidContactFile_skips() throws Exception {
        JsonSerializableContactManager dataFromFile = JsonUtil.readJsonFile(INVALID_CONTACT_FILE,
                JsonSerializableContactManager.class).get();
        assertEquals(0, dataFromFile.toModelType().getItemList().size());
    }

    @Test
    public void toModelType_duplicateContacts_skips() throws Exception {
        JsonSerializableContactManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CONTACT_FILE,
                JsonSerializableContactManager.class).get();
        assertEquals(1, dataFromFile.toModelType().getItemList().size());
    }

}
