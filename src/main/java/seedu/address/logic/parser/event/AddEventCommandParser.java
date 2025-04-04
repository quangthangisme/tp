package seedu.address.logic.parser.event;

import static seedu.address.logic.EventMessages.MESSAGE_NEGATIVE_DURATION;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.create.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.PrefixAlias;
import seedu.address.logic.parser.PrefixAliasListBuilder;
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
        PrefixAlias namePrefix = EventCliSyntax.PREFIX_ALIAS_EVENT_NAME;
        PrefixAlias startPrefix = EventCliSyntax.PREFIX_ALIAS_EVENT_START;
        PrefixAlias endPrefix = EventCliSyntax.PREFIX_ALIAS_EVENT_END;
        PrefixAlias locationPrefix = EventCliSyntax.PREFIX_ALIAS_EVENT_LOCATION;
        PrefixAlias tagPrefix = EventCliSyntax.PREFIX_ALIAS_EVENT_TAG;
        Prefix[] listOfPrefixes = new PrefixAliasListBuilder()
                .add(namePrefix, startPrefix, endPrefix, locationPrefix, tagPrefix)
                .toArray();
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
        Name name = EventParserUtil.parseName(argMultimap.getValue(namePrefix).get());
        Datetime startTime = EventParserUtil.parseDateTime(argMultimap.getValue(startPrefix).get());
        Datetime endTime = EventParserUtil.parseDateTime(argMultimap.getValue(endPrefix).get());
        Location location = EventParserUtil.parseLocation(argMultimap.getValue(locationPrefix).get());
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
