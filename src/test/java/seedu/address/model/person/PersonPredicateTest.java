package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class PersonPredicateTest {

    @Test
    public void equals() {
        Map<Column, FilterCriteria> firstPredicateMap = new HashMap<>();
        firstPredicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Collections.singletonList("Alice")));

        Map<Column, FilterCriteria> secondPredicateMap = new HashMap<>();
        secondPredicateMap.put(Column.EMAIL, new FilterCriteria(Operator.OR, Collections.singletonList("gmail.com")));

        PersonPredicate firstPredicate = new PersonPredicate(firstPredicateMap);
        PersonPredicate secondPredicate = new PersonPredicate(secondPredicateMap);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        PersonPredicate firstPredicateCopy = new PersonPredicate(firstPredicateMap);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different predicate -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // AND operator - all keywords must match
        Map<Column, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Arrays.asList("Alice", "Pauline")));
        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice Pauline matches

        // OR operator - at least one keyword must match
        predicateMap.clear();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.OR, Arrays.asList("Alice", "Bob")));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice matches

        // NAND operator - at least one keyword must not match
        predicateMap.clear();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.NAND, Arrays.asList("Bob", "Charlie")));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice doesn't contain Bob or Charlie

        // NOR operator - all keywords must not match
        predicateMap.clear();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("Bob", "Charlie")));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice doesn't contain Bob or Charlie
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // AND operator
        Map<Column, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Arrays.asList("Alice", "Bob")));
        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(BENSON)); // Benson doesn't contain Alice and Bob

        // OR operator
        predicateMap.clear();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.OR, Arrays.asList("Charlie", "David")));
        predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice doesn't contain Charlie or David

        // NAND operator
        predicateMap.clear();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.NAND, Arrays.asList("Alice")));
        predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice contains Alice, so NAND fails

        // NOR operator
        predicateMap.clear();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("Alice", "Bob")));
        predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice contains Alice, so NOR fails
    }

    @Test
    public void test_multipleColumns_matchesAccordingly() {
        // Test multiple columns with AND relationship (implicit between columns)
        Map<Column, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(Column.NAME, new FilterCriteria(Operator.AND, Collections.singletonList("Alice")));
        predicateMap.put(Column.EMAIL, new FilterCriteria(Operator.AND, Collections.singletonList("example")));

        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice has both Alice in name and example in email
        assertFalse(predicate.test(BENSON)); // Benson doesn't have Alice in name
    }

    @Test
    public void test_differentFields_returnsCorrectResults() {
        // Test ID field
        Map<Column, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(Column.ID, new FilterCriteria(Operator.AND, Collections.singletonList(ALICE.getId().fullId)));
        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));

        // Test PHONE field
        predicateMap.clear();
        predicateMap.put(Column.PHONE, new FilterCriteria(Operator.AND, Collections.singletonList(ALICE.getPhone().value)));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));

        // Test TAG field
        predicateMap.clear();
        String tag = ALICE.getTags().stream().findFirst().get().tagName;
        predicateMap.put(Column.TAG, new FilterCriteria(Operator.AND, Collections.singletonList(tag)));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));
    }
}