package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.InfoPersonCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InfoCommand object
 */
public class InfoPersonCommandParser implements Parser<InfoPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the InfoCommand and returns an
     * InfoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InfoPersonCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new InfoPersonCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            InfoPersonCommand.MESSAGE_USAGE), pe);
        }
    }
}
