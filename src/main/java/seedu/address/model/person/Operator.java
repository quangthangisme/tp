package seedu.address.model.person;

public enum Operator {
    AND("and"),
    OR("or"),
    NAND("nand"),
    NOR("nor");

    private final String name;

    Operator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}