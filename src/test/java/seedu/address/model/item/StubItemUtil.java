package seedu.address.model.item;

public class StubItemUtil {
    public static class Stub implements Item {
        String id;
        String notId;

        Stub(String id, String notId) {
            this.id = id;
            this.notId = notId;
        }
    }

    public static class DuplicateStubChecker implements DuplicateChecker<Stub> {
        @Override
        public boolean check(Stub first, Stub second) {
            return first.id.equals(second.id);
        }
    }

    public static class DuplicateStubException extends RuntimeException {
    }

    public static class StubNotFoundException extends RuntimeException {
    }

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

    public static class StubManager extends ItemManager<Stub> {
        public StubManager() {
            super(new UniqueStubList());
        }
    }

    public static class StubManagerAndList extends ItemManagerWithFilteredList<Stub> {
        public StubManagerAndList() {
            super(new StubManager());
        }

        public StubManagerAndList(ItemManager<Stub> stubManager) {
            super(stubManager);
        }
    }

    public static final Stub STUB_A = new Stub("STUB A", "I'm stub A");
    public static final Stub STUB_A_DUPLICATE = new Stub("STUB A", "I'm not stub A");
    public static final Stub STUB_B = new Stub("STUB B", "I'm stub A");
}
