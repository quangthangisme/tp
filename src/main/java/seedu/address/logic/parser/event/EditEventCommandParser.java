package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditEventCommand;
import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
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
        PrefixAlias namePrefix = EventCliAlias.EVENT_NAME_PREFIX_ALIAS;
        PrefixAlias startPrefix = EventCliAlias.EVENT_START_PREFIX_ALIAS;
        PrefixAlias endPrefix = EventCliAlias.EVENT_END_PREFIX_ALIAS;
        PrefixAlias locationPrefix = EventCliAlias.EVENT_LOCATION_PREFIX_ALIAS;
        PrefixAlias tagPrefix = EventCliAlias.EVENT_TAG_PREFIX_ALIAS;
        PrefixAlias contactPrefix = EventCliAlias.EVENT_LINKED_CONTACT_PREFIX_ALIAS;
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(namePrefix, startPrefix, endPrefix, locationPrefix, tagPrefix, contactPrefix)
                .toArray();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(namePrefix).isPresent()) {
            editEventDescriptor.setName(
                    EventParserUtil.parseName(argMultimap.getValue(namePrefix).get()));
        }
        if (argMultimap.getValue(startPrefix).isPresent()) {
            editEventDescriptor.setStartTime(
                    EventParserUtil.parseDateTime(argMultimap.getValue(startPrefix).get()));
        }
        if (argMultimap.getValue(endPrefix).isPresent()) {
            editEventDescriptor.setEndTime(
                    EventParserUtil.parseDateTime(argMultimap.getValue(endPrefix).get()));
        }
        if (argMultimap.getValue(locationPrefix).isPresent()) {
            editEventDescriptor.setLocation(
                    EventParserUtil.parseLocation(argMultimap.getValue(locationPrefix).get()));
        }
        if (argMultimap.getValue(tagPrefix).isPresent()) {
            editEventDescriptor.setTags(
                    ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get()));
        }
        Optional<List<Index>> linkedContactIndices = Optional.empty();
        if (argMultimap.getValue(contactPrefix).isPresent()) {
            linkedContactIndices = Optional.of(ParserUtil.parseIndices(argMultimap
                    .getValue(contactPrefix).get()));
            editEventDescriptor.setAttendance(new Attendance());
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor, linkedContactIndices);
    }

}
