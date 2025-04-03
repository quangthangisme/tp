package seedu.address.logic.commands.read;

import static seedu.address.logic.Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.TODO_COMMAND_WORD;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_STATUS_LONG;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManagerAndList;
import seedu.address.model.todo.predicate.TodoContactPredicate;
import seedu.address.model.todo.predicate.TodoPredicate;

/**
 * Filters and lists all events based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterTodoCommand extends FilterCommand<TodoManagerAndList, Todo> {

    public static final String MESSAGE_USAGE = TODO_COMMAND_WORD + " " + COMMAND_WORD
            + ": Filters todos based on specified criteria.\n"
            + "Parameters: COL/ [<OP>:] <VALUE(s)> [...]\n"

            + "- COL/ : Column to filter on (" + PREFIX_TODO_NAME_LONG + ", " + PREFIX_TODO_DEADLINE_LONG
            + ", " + PREFIX_TODO_LOCATION_LONG + ", " + PREFIX_TODO_STATUS_LONG + ", "
            + PREFIX_TODO_LINKED_CONTACT_LONG + ")\n"

            + "- <OP>: : Operator (and, or, nand, nor) to apply to the column criterion. Defaults"
            + " to 'and' if not specified. Cannot be applied to status criterion.\n"

            + "- <VALUE(s)>: One or more values to filter by.\n"
            + "    + For name and location, use keywords separated by whitespaces.\n"
            + "    + For deadline and location, use closed intervals separated by whitespaces. "
            + "Each interval is in the format \"(<INTERVAL_START>/<INTERVAL_END>)\". The "
            + "interval start and end can be in the format YY-MM-DD HH:MM, where HH is in 24-hour "
            + "format, or can be - to specify no lower bound or upper bound. At least one of the "
            + "two bounds must be specified.\n"
            + "    + For status, use \"true\" for done and \"false\" for not done.\n"
            + "    + For linked contacts, use the indices in the currently displayed contact list.\n"

            + "Examples:\n"

            + "1. " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TODO_NAME_LONG + " "
            + Operator.OR.getName() + ": Grading REPORT\n"
            + "   Find todos whose name contains at least one of the keywords \"grading\" or "
            + "\"report.\"\n"

            + "2. " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TODO_NAME_LONG
            + "CS1010S GrAdIng " + PREFIX_TODO_DEADLINE_LONG + " " + Operator.OR.getName()
            + ": (25-03-13 23:59/25-03-20 23:59) (25-03-27 23:59/-)\n"
            + "   Find todos whose name contains both the keywords \"CS1010S\" and "
            + "\"grading\" and whose deadline is between 25-03-13 23:59 and 25-03-20 23:59 "
            + "inclusive or not before 25-03-27 23:59.\n"

            + "3. " + TODO_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_TODO_LOCATION_LONG
            + " " + Operator.NAND.getName() + ": NUS Home " + PREFIX_TODO_STATUS_LONG + "false\n"
            + "   Find todos whose location does not contain the keywords \"NUS\" or "
            + "\"home\" and which are not done yet.\n";

    private final TodoPredicate todoPredicate;
    private final Optional<Pair<Operator, List<Index>>> contactFilterOpt;

    /**
     * Creates a {@code FilterTodoCommand} to filter items that match the given predicates.
     */
    public FilterTodoCommand(TodoPredicate todoPredicate,
                             Optional<Pair<Operator, List<Index>>> contactFilterOpt) {
        super(Model::getTodoManagerAndList);
        this.todoPredicate = todoPredicate;
        this.contactFilterOpt = contactFilterOpt;
    }


    @Override
    public Predicate<Todo> createPredicate(Model model) throws CommandException {
        if (contactFilterOpt.isPresent()) {
            List<Contact> filteredContacts = model.getContactManagerAndList().getFilteredItemsList();

            for (Index index : contactFilterOpt.get().second()) {
                if (index.getZeroBased() >= filteredContacts.size()) {
                    throw new CommandException(String.format(
                            MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX, index.getOneBased()));
                }
            }

            List<Contact> contacts = contactFilterOpt.get().second().stream()
                    .map(index -> filteredContacts.get(index.getZeroBased())).toList();

            todoPredicate.setContactPredicate(
                    new TodoContactPredicate(contactFilterOpt.get().first(), contacts));
        }

        return todoPredicate;
    }

}
