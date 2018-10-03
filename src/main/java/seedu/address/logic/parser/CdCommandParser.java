package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;

import seedu.address.logic.commands.CdCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CdCommand object
 */
public class CdCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the CdCommand
     * and returns an CdCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CdCommand parse(String args) throws ParseException {
        try {
            Path dir = ParserUtil.parseFilePath(args);
            return new CdCommand(dir);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CdCommand.MESSAGE_USAGE), pe);
        }
    }
}
