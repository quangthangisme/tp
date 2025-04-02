package seedu.address.logic.parser.event;

import static seedu.address.logic.EventMessages.MESSAGE_NEGATIVE_DURATION;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.create.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.item.commons.Datetime;
import seedu.address.model.item.commons.Location;
import seedu.address.model.item.commons.Name;
import seedu.address.model.item.commons.Tag;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand and
     * returns an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddEventCommand parse(String args) throws ParseException {
        NamePrefix namePrefix = new NamePrefix(AddEventCommand.COMMAND_WORD);
        StartPrefix startPrefix = new StartPrefix(AddEventCommand.COMMAND_WORD);
        EndPrefix endPrefix = new EndPrefix(AddEventCommand.COMMAND_WORD);
        LocationPrefix locationPrefix = new LocationPrefix(AddEventCommand.COMMAND_WORD);
        TagPrefix tagPrefix = new TagPrefix(AddEventCommand.COMMAND_WORD);
        Prefix[] listOfPrefixes = Stream.of(
                namePrefix.getAll(),
                startPrefix.getAll(),
                endPrefix.getAll(),
                locationPrefix.getAll(),
                tagPrefix.getAll()
        ).flatMap(Arrays::stream).toArray(Prefix[]::new);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);
        if (namePrefix.getValue(argMultimap).isEmpty()
                || startPrefix.getValue(argMultimap).isEmpty()
                || endPrefix.getValue(argMultimap).isEmpty()
                || locationPrefix.getValue(argMultimap).isEmpty()
                || tagPrefix.getValue(argMultimap).isEmpty()
                || !argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        // if (!argMultimap.arePrefixesPresent(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
        //         PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG)
        //         || !argMultimap.getPreamble().isEmpty()) {
        //     throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
        //             AddEventCommand.MESSAGE_USAGE));
        // }

        // Name name = EventParseUtil.parseName(argMultimap.getValue(PREFIX_EVENT_NAME_LONG).get());
        // Datetime startTime = EventParseUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START_LONG).get());
        // Datetime endTime = EventParseUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_END_LONG).get());
        // Location location = EventParseUtil.parseLocation(argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).get());

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);
        Name name = EventParseUtil.parseName(namePrefix.getValue(argMultimap).get());
        Datetime startTime = EventParseUtil.parseDateTime(startPrefix.getValue(argMultimap).get());
        Datetime endTime = EventParseUtil.parseDateTime(endPrefix.getValue(argMultimap).get());
        Location location = EventParseUtil.parseLocation(locationPrefix.getValue(argMultimap).get());
        if (!startTime.isBefore(endTime)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_NEGATIVE_DURATION));
        }
        Set<Tag> tagSet = new HashSet<>();
        if (argMultimap.arePrefixesPresent(tagPrefix.getAll())) {
            tagSet = ParserUtil.parseTags(tagPrefix.getValue(argMultimap).get());
        }

        Event event = new Event(name, startTime, endTime, location, tagSet);
        return new AddEventCommand(event);
    }
}
