package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_TAG_LONG;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.update.EditEventCommand;
import seedu.address.logic.commands.update.EditEventDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.contact.ContactParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.commons.Tag;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME_LONG,
                PREFIX_EVENT_START_LONG, PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_TAG_LONG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditEventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
                PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_EVENT_NAME_LONG).isPresent()) {
            editEventDescriptor.setName(
                    EventParseUtil.parseName(argMultimap.getValue(PREFIX_EVENT_NAME_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_START_LONG).isPresent()) {
            editEventDescriptor.setStartTime(
                    EventParseUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_END_LONG).isPresent()) {
            editEventDescriptor.setEndTime(
                    EventParseUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_END_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).isPresent()) {
            editEventDescriptor.setLocation(
                    EventParseUtil.parseLocation(argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_TAG_LONG).isPresent()) {
            Collection<String> tags = argMultimap.getAllValues(PREFIX_EVENT_TAG_LONG);
            Set<Tag> tagSet;
            // Special allowance for empty tag to remove all tags
            if (tags.size() == 1 && tags.contains("")) {
                tagSet = Collections.emptySet();
            } else {
                tagSet = ContactParserUtil.parseTags(tags);
            }
            editEventDescriptor.setTags(tagSet);
        }

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }

}
