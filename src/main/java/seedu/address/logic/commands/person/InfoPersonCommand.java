package seedu.address.logic.commands.person;

import static seedu.address.logic.Messages.MESSAGE_DISPLAY_SPECIFIC_PERSON_INFO;
import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.DisplayInformationCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class InfoPersonCommand extends DisplayInformationCommand<Person> {

    public static final String COMMAND_WORD = "info";

    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD + ": Displays the complete "
            + "information belonging to the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " 2";

    public InfoPersonCommand(Index targetIndex) {
        super(targetIndex, Model::getPersonManagerAndList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoPersonCommand otherInfoCommand)) {
            return false;
        }

        return index.equals(otherInfoCommand.index);
    }


    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    }

    @Override
    public String getInformationMessage(Person personToDisplay) {
        return String.format(MESSAGE_DISPLAY_SPECIFIC_PERSON_INFO, personToDisplay.getName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
