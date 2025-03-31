package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.item.StubItemUtil.STUB_A;
import static seedu.address.model.item.StubItemUtil.STUB_A_DUPLICATE;
import static seedu.address.model.item.StubItemUtil.STUB_B;
import static seedu.address.model.item.StubItemUtil.Stub;
import static seedu.address.model.item.StubItemUtil.StubManager;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class ItemManagerTest {

    private final ItemManager<Stub> stubManager = new StubManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), stubManager.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> stubManager.resetData(null));
    }

    @Test
    public void resetData_withValidManager_replacesData() {
        ItemManager<Stub> newData = new StubManager();
        newData.addItem(STUB_A);
        newData.addItem(STUB_B);
        stubManager.resetData(newData);
        assertEquals(newData, stubManager);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> stubManager.hasItem(null));
    }

    @Test
    public void hasItem_itemNotInManager_returnsFalse() {
        assertFalse(stubManager.hasItem(STUB_A));
    }

    @Test
    public void hasItem_itemInManager_returnsTrue() {
        stubManager.addItem(STUB_A);
        assertTrue(stubManager.hasItem(STUB_A));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInManager_returnsTrue() {
        stubManager.addItem(STUB_A);
        assertTrue(stubManager.hasItem(STUB_A_DUPLICATE));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                stubManager.getItemList().remove(0));
    }
}
