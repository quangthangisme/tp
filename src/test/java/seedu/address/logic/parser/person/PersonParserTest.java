package seedu.address.logic.parser.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.person.AddPersonCommand;
import seedu.address.logic.commands.person.ClearPersonCommand;
import seedu.address.logic.commands.person.DeletePersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.logic.commands.person.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.person.ListPersonCommand;
import seedu.address.logic.parser.ParserImpl;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class PersonParserTest {

    private final ParserImpl parser = new ParserImpl();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command =
                (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(PERSON_COMMAND_WORD + " "
                + ClearPersonCommand.COMMAND_WORD) instanceof ClearPersonCommand);
        assertTrue(parser.parseCommand(PERSON_COMMAND_WORD + " "
                + ClearPersonCommand.COMMAND_WORD + " 3") instanceof ClearPersonCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                PERSON_COMMAND_WORD + " " + DeletePersonCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command =
                (EditPersonCommand) parser.parseCommand(PERSON_COMMAND_WORD + " "
                        + EditPersonCommand.COMMAND_WORD + " "
                        + INDEX_FIRST.getOneBased() + " "
                        + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(PERSON_COMMAND_WORD + " "
                + ListPersonCommand.COMMAND_WORD) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(PERSON_COMMAND_WORD + " "
                + ListPersonCommand.COMMAND_WORD + " 3") instanceof ListPersonCommand);
    }
}
