package seedu.address.logic.commands.person;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.FilterCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPredicate;

/**
 * Filters and lists all persons in address book based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterPersonCommand extends FilterCommand<Person> {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters persons based on specified criteria "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: <col>/ [<op>:] <value(s)> [...]\n"
            + "- <col>/ : Column to filter on (" + PREFIX_NAME + ", " + PREFIX_PHONE + ", " + PREFIX_EMAIL + ", "
            + PREFIX_ID + ", " + PREFIX_COURSE + ", " + PREFIX_GROUP + ", " + PREFIX_TAG + ")\n"
            + "- <op>: : Operator (and, or, nand, nor). Defaults to 'and' if not specified\n"
            + "- <value(s)>: One or more values to filter by. Use quotes for values with spaces.\n"
            + "Examples:\n"
            + "1. " + COMMAND_WORD + " " + PREFIX_ID + " or: 12 13\n"
            + "   Find students with ID 12 or 13.\n"
            + "2. " + COMMAND_WORD + " " + PREFIX_NAME + "\"Darren Tan\" " + PREFIX_COURSE + " CS1010S "
            + PREFIX_GROUP + "or: T01 T02 T03\n"
            + "   Find contacts with \"Darren Tan\" in their name who"
            + " enroll in course CS1010S and class T01, T02, or T03.\n"
            + "3. " + COMMAND_WORD + " " + PREFIX_NAME + "nand: \"My enemy\" Hater "
            + PREFIX_TAG + "and: handsome smart\n"
            + "   Find contacts whose names do not contain \"My enemy\" and"
            + " \"Hater\" and are tagged with both \"handsome\" and \"smart\".";

    /**
     * Constructs a FilterPersonCommand with the given predicate.
     *
     * @param predicate the predicate used to filter persons
     */
    public FilterPersonCommand(PersonPredicate predicate) {
        super(predicate, Model::getPersonManagerAndList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPersonCommand otherFilterCommand)) {
            return false;
        }

        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String getResultOverviewMessage(int numberOfResults) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, numberOfResults);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
