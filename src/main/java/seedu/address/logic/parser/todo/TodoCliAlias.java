package seedu.address.logic.parser.todo;

import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_SHORT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_SHORT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_SHORT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_SHORT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_SHORT;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_TAG_SHORT;

import seedu.address.logic.parser.PrefixAlias;

/**
 * Contains Command Line Interface (CLI) syntax alias definitions for {@code Todo}.
 */
public class TodoCliAlias {
    public static final PrefixAlias TODO_DEADLINE_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_TODO_DEADLINE_LONG, PREFIX_TODO_DEADLINE_SHORT);
    public static final PrefixAlias TODO_LINKED_CONTACT_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_TODO_LINKED_CONTACT_LONG, PREFIX_TODO_LINKED_CONTACT_SHORT);
    public static final PrefixAlias TODO_LOCATION_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_TODO_LOCATION_LONG, PREFIX_TODO_LOCATION_SHORT);
    public static final PrefixAlias TODO_NAME_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_TODO_NAME_LONG, PREFIX_TODO_NAME_SHORT);
    public static final PrefixAlias TODO_STATUS_ALIAS = new PrefixAlias(
            PREFIX_TODO_STATUS_LONG, PREFIX_TODO_STATUS_SHORT);
    public static final PrefixAlias TODO_TAG_PREFIX_ALIAS = new PrefixAlias(
            PREFIX_TODO_TAG_LONG, PREFIX_TODO_TAG_SHORT);
}
