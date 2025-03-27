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
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;

public class PersonPredicateTest {

    @Test
    public void equals() {
        Map<PersonColumn, FilterCriteria> firstPredicateMap = new HashMap<>();
        firstPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("Alice")));

        Map<PersonColumn, FilterCriteria> secondPredicateMap = new HashMap<>();
        secondPredicateMap.put(PersonColumn.EMAIL, new FilterCriteria(Operator.OR,
                Collections.singletonList("gmail.com")));

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
        Map<PersonColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND, Arrays.asList("Alice",
                "Pauline")));
        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice Pauline matches

        // OR operator - at least one keyword must match
        predicateMap.clear();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList("Alice",
                "Bob")));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice matches

        // NAND operator - at least one keyword must not match
        predicateMap.clear();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.NAND, Arrays.asList("Bob",
                "Charlie")));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice doesn't contain Bob or Charlie

        // NOR operator - all keywords must not match
        predicateMap.clear();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("Bob",
                "Charlie")));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice doesn't contain Bob or Charlie
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // AND operator
        Map<PersonColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND, Arrays.asList("Alice",
                "Bob")));
        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(BENSON)); // Benson doesn't contain Alice and Bob

        // OR operator
        predicateMap.clear();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList(
                "Charlie", "David")));
        predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice doesn't contain Charlie or David

        // NAND operator
        predicateMap.clear();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.NAND, List.of("Alice")));
        predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice contains Alice, so NAND fails

        // NOR operator
        predicateMap.clear();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("Alice",
                "Bob")));
        predicate = new PersonPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice contains Alice, so NOR fails
    }

    @Test
    public void test_multipleColumns_matchesAccordingly() {
        // Test multiple columns with AND relationship (implicit between columns)
        Map<PersonColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("Alice")));
        predicateMap.put(PersonColumn.EMAIL, new FilterCriteria(Operator.AND,
                Collections.singletonList("example")));

        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice has both Alice in name and example in email
        assertFalse(predicate.test(BENSON)); // Benson doesn't have Alice in name
    }

    @Test
    public void test_differentFields_returnsCorrectResults() {
        // Test ID field
        Map<PersonColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(PersonColumn.ID, new FilterCriteria(Operator.AND,
                Collections.singletonList(ALICE.getId().fullId)));
        PersonPredicate predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));

        // Test PHONE field
        predicateMap.clear();
        predicateMap.put(PersonColumn.PHONE, new FilterCriteria(Operator.AND,
                Collections.singletonList(ALICE.getPhone().value)));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));

        // Test TAG field
        predicateMap.clear();
        String tag = ALICE.getTags().stream().findFirst().get().tagName;
        predicateMap.put(PersonColumn.TAG, new FilterCriteria(Operator.AND,
                Collections.singletonList(tag)));
        predicate = new PersonPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));
    }
}
