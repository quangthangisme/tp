package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditEventCommand;
import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
        ArgumentMultimap argMultimap = tokenizeArgs(args);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());

        EditEventDescriptor editEventDescriptor = buildEditEventDescriptor(argMultimap);
        Optional<List<Index>> linkedContactIndices = parseLinkedContacts(argMultimap, editEventDescriptor);

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor, linkedContactIndices);
    }

    private ArgumentMultimap tokenizeArgs(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME_LONG,
                PREFIX_EVENT_START_LONG, PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG,
                PREFIX_EVENT_TAG_LONG, PREFIX_EVENT_LINKED_CONTACT_LONG);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
                PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_TAG_LONG,
                PREFIX_EVENT_LINKED_CONTACT_LONG);

        return argMultimap;
    }

    private EditEventDescriptor buildEditEventDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_EVENT_NAME_LONG).isPresent()) {
            editEventDescriptor.setName(
                    EventParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT_NAME_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_START_LONG).isPresent()) {
            editEventDescriptor.setStartTime(
                    EventParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_END_LONG).isPresent()) {
            editEventDescriptor.setEndTime(
                    EventParserUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_END_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).isPresent()) {
            editEventDescriptor.setLocation(
                    EventParserUtil.parseLocation(argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_TAG_LONG).isPresent()) {
            editEventDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(PREFIX_EVENT_TAG_LONG).get()));
        }

        return editEventDescriptor;
    }

    private Optional<List<Index>> parseLinkedContacts(ArgumentMultimap argMultimap,
                                                      EditEventDescriptor editEventDescriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).isPresent()) {
            List<Index> contactIndices = ParserUtil.parseIndices(
                    argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).get());
            editEventDescriptor.setAttendance(new Attendance());
            return Optional.of(contactIndices);
        }
        return Optional.empty();
    }

}
