package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditEventCommand;
import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Attendance;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        NamePrefix namePrefix = new NamePrefix(EditEventCommand.COMMAND_WORD);
        StartPrefix startPrefix = new StartPrefix(EditEventCommand.COMMAND_WORD);
        EndPrefix endPrefix = new EndPrefix(EditEventCommand.COMMAND_WORD);
        LocationPrefix locationPrefix = new LocationPrefix(EditEventCommand.COMMAND_WORD);
        TagPrefix tagPrefix = new TagPrefix(EditEventCommand.COMMAND_WORD);
        ContactPrefix contactPrefix = new ContactPrefix(EditEventCommand.COMMAND_WORD);
        Prefix[] listOfPrefixes = Stream.of(
                namePrefix.getAll(),
                startPrefix.getAll(),
                endPrefix.getAll(),
                locationPrefix.getAll(),
                tagPrefix.getAll(),
                contactPrefix.getAll()
        ).flatMap(Arrays::stream).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (namePrefix.getValue(argMultimap).isPresent()) {
            editEventDescriptor.setName(
                    EventParseUtil.parseName(namePrefix.getValue(argMultimap).get()));
        }
        if (startPrefix.getValue(argMultimap).isPresent()) {
            editEventDescriptor.setStartTime(
                    EventParseUtil.parseDateTime(startPrefix.getValue(argMultimap).get()));
        }
        if (endPrefix.getValue(argMultimap).isPresent()) {
            editEventDescriptor.setEndTime(
                    EventParseUtil.parseDateTime(endPrefix.getValue(argMultimap).get()));
        }
        if (locationPrefix.getValue(argMultimap).isPresent()) {
            editEventDescriptor.setLocation(
                    EventParseUtil.parseLocation(locationPrefix.getValue(argMultimap).get()));
        }
        if (tagPrefix.getValue(argMultimap).isPresent()) {
            editEventDescriptor.setTags(
                    ParserUtil.parseTags(tagPrefix.getValue(argMultimap).get()));
        }
        List<Index> linkedContactIndices = List.of();
        if (contactPrefix.getValue(argMultimap).isPresent()) {
            linkedContactIndices =
                    ParserUtil.parseIndices(contactPrefix.getValue(argMultimap).get());
            editEventDescriptor.setAttendance(new Attendance());
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor, linkedContactIndices);
    }

}
