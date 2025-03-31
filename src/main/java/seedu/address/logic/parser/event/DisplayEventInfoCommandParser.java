package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.read.InfoEventCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DisplayEventInfoCommand object
 */
public class DisplayEventInfoCommandParser implements Parser<InfoEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DisplayEventInfomationCommand and returns an DisplayEventInfomation
     * Command object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public InfoEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InfoEventCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(args);
        return new InfoEventCommand(index);
    }
}
