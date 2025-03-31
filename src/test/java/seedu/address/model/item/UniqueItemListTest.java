package seedu.address.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.item.StubItemUtil.DuplicateStubException;
import static seedu.address.model.item.StubItemUtil.STUB_A;
import static seedu.address.model.item.StubItemUtil.STUB_A_DUPLICATE;
import static seedu.address.model.item.StubItemUtil.STUB_B;
import static seedu.address.model.item.StubItemUtil.Stub;
import static seedu.address.model.item.StubItemUtil.StubNotFoundException;
import static seedu.address.model.item.StubItemUtil.UniqueStubList;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class UniqueItemListTest {

    private final UniqueItemList<Stub> uniqueList = new UniqueStubList();

    private final UniqueItemList<Stub> expectedUniqueList =
            new UniqueStubList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.contains(null));
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueList.contains(STUB_A));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueList.add(STUB_A);
        assertTrue(uniqueList.contains(STUB_A));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueList.add(STUB_A);
        assertTrue(uniqueList.contains(STUB_A_DUPLICATE));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.add(null));
    }

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        uniqueList.add(STUB_A);
        assertThrows(DuplicateStubException.class, () -> uniqueList.add(STUB_A));
    }

    @Test
    public void setItem_nullTargetItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setItem(null, STUB_A));
    }

    @Test
    public void setItem_nullEditedItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.setItem(STUB_A, null));
    }

    @Test
    public void setItem_targetItemNotInList_throwsItemNotFoundException() {
        assertThrows(StubNotFoundException.class, () -> uniqueList.setItem(STUB_A,
                STUB_A));
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueList.add(STUB_A);
        uniqueList.setItem(STUB_A, STUB_A);
        expectedUniqueList.add(STUB_A);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueList.add(STUB_A);
        uniqueList.setItem(STUB_A, STUB_A_DUPLICATE);
        expectedUniqueList.add(STUB_A_DUPLICATE);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueList.add(STUB_A);
        uniqueList.setItem(STUB_A, STUB_B);
        expectedUniqueList.add(STUB_B);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueList.add(STUB_A);
        uniqueList.add(STUB_B);
        assertThrows(DuplicateStubException.class, () -> uniqueList.setItem(STUB_A, STUB_B));
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueList.remove(null));
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        assertThrows(StubNotFoundException.class, () -> uniqueList.remove(STUB_A));
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueList.add(STUB_A);
        uniqueList.remove(STUB_A);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueList.setItems((UniqueItemList<Stub>) null));
    }

    @Test
    public void setItems_uniqueList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueList.add(STUB_A);
        expectedUniqueList.add(STUB_B);
        uniqueList.setItems(expectedUniqueList);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setItems_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
                -> uniqueList.setItems((List<Stub>) null));
    }

    @Test
    public void setItems_list_replacesOwnListWithProvidedList() {
        uniqueList.add(STUB_A);
        List<Stub> stubList = Collections.singletonList(STUB_B);
        uniqueList.setItems(stubList);
        expectedUniqueList.add(STUB_B);
        assertEquals(expectedUniqueList, uniqueList);
    }

    @Test
    public void setItems_listWithDuplicateItems_throwsDuplicateItemException() {
        List<Stub> listWithDuplicateItems = Arrays.asList(STUB_A, STUB_A);
        assertThrows(DuplicateStubException.class, () ->
                uniqueList.setItems(listWithDuplicateItems));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueList.asUnmodifiableObservableList().toString(),
                uniqueList.toString());
    }
}
