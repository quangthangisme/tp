package seedu.address.model.item;

/**
 * Stubs for item-related classes.
 */
public class StubItemUtil {

    /**
     * An stub for {@code Item}.
     */
    public static class Stub implements Item {
        private final String id;
        private final String notId;

        /**
         * Constructor for the stub.
         */
        public Stub(String id, String notId) {
            this.id = id;
            this.notId = notId;
        }
    }

    /**
     * An stub for {@code DuplicateChecker}.
     */
    public static class DuplicateStubChecker implements DuplicateChecker<Stub> {
        @Override
        public boolean check(Stub first, Stub second) {
            return first.id.equals(second.id);
        }
    }

    /**
     * An stub for {@code DuplicateException}.
     */
    public static class DuplicateStubException extends RuntimeException {
    }

    /**
     * An stub for {@code NotFoundException}.
     */
    public static class StubNotFoundException extends RuntimeException {
    }

    /**
     * An stub for {@code UniqueItemList}.
     */
    public static class UniqueStubList extends UniqueItemList<Stub> {
        public UniqueStubList() {
            super(new DuplicateStubChecker());
        }

        @Override
        public void throwDuplicateException() {
            throw new DuplicateStubException();
        }

        @Override
        public void throwNotFoundException() {
            throw new StubNotFoundException();
        }
    }

    /**
     * An stub for {@code ItemManager}.
     */
    public static class StubManager extends ItemManager<Stub> {
        public StubManager() {
            super(new UniqueStubList());
        }
    }

    public static final Stub STUB_A = new Stub("STUB A", "I'm stub A");
    public static final Stub STUB_A_DUPLICATE = new Stub("STUB A", "I'm not stub A");
    public static final Stub STUB_B = new Stub("STUB B", "I'm stub A");
}
