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
        NamePrefix namePrefix = new NamePrefix();
        StartPrefix startPrefix = new StartPrefix();
        EndPrefix endPrefix = new EndPrefix();
        LocationPrefix locationPrefix = new LocationPrefix();
        TagPrefix tagPrefix = new TagPrefix();
        Prefix[] listOfPrefixes = Stream.of(
                namePrefix.getAll(),
                startPrefix.getAll(),
                endPrefix.getAll(),
                locationPrefix.getAll(),
                tagPrefix.getAll()
        ).flatMap(Arrays::stream).toArray(Prefix[]::new);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, listOfPrefixes);

        if (argMultimap.getValue(namePrefix).isEmpty()
                || argMultimap.getValue(startPrefix).isEmpty()
                || argMultimap.getValue(endPrefix).isEmpty()
                || argMultimap.getValue(locationPrefix).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(listOfPrefixes);
        Name name = EventParseUtil.parseName(argMultimap.getValue(namePrefix).get());
        Datetime startTime = EventParseUtil.parseDateTime(argMultimap.getValue(startPrefix).get());
        Datetime endTime = EventParseUtil.parseDateTime(argMultimap.getValue(endPrefix).get());
        Location location = EventParseUtil.parseLocation(argMultimap.getValue(locationPrefix).get());
        if (!startTime.isBefore(endTime)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_NEGATIVE_DURATION));
        }
        Set<Tag> tagSet = new HashSet<>();
        if (argMultimap.arePrefixAliasPresent(tagPrefix)) {
            tagSet = ParserUtil.parseTags(argMultimap.getValue(tagPrefix).get());
        }

        Event event = new Event(name, startTime, endTime, location, tagSet);
        return new AddEventCommand(event);
    }
}
