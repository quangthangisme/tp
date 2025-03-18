package seedu.address.model.person;

public enum Column {
    NAME("n"),
    PHONE("p"),
    EMAIL("e"),
    ID("i"),
    COURSE("c"),
    GROUP("g"),
    TAG("t");

    private final String abbrev;

    Column(String abbrev) {
        this.abbrev = abbrev;
    }
}