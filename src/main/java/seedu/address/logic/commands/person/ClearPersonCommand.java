package seedu.address.logic.commands.person;

import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;

import seedu.address.logic.abstractcommand.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonManager;

/**
 * Clears the address book.
 */
public class ClearPersonCommand extends ClearCommand<Person> {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD + ": Clears the address book.";

    /**
     * Creates a {@code ClearPersonCommand} to clear the person list in the model.
     */
    public ClearPersonCommand() {
        super(Model::getPersonManagerAndList, PersonManager::new);
    }


    @Override
    public String getSuccessMessage() {
        return MESSAGE_SUCCESS;
    }
}
