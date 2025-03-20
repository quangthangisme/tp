package seedu.address.logic.commands.person;

import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;

import seedu.address.logic.abstractcommand.ListCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListPersonCommand extends ListCommand<Person> {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD + ": Lists all persons.";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    /**
     * Creates a {@code ListPersonCommand}.
     */
    public ListPersonCommand() {
        super(Model::getPersonManagerAndList);
    }

    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
