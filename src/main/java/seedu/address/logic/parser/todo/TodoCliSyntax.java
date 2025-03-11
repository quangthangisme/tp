package seedu.address.logic.parser.todo;

import seedu.address.logic.parser.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple todo commands.
 */
public class TodoCliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TODO_NAME = new Prefix("-n");
    public static final Prefix PREFIX_TODO_DEADLINE = new Prefix("-d");
    public static final Prefix PREFIX_TODO_LOCATION = new Prefix("-l");

}
