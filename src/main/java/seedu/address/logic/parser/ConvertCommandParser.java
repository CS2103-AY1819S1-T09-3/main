package seedu.address.logic.parser;

//@@author lancelotwilow
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ConvertCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transformation.Transformation;

/**
 * Parses input arguments and creates a new ExampleCommand object
 */
public class ConvertCommandParser implements Parser<ConvertCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExampleCommand
     * and returns an ExampleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConvertCommand parse(String args) throws ParseException {
        try {
            String[] all = args.split(" ");
            if (all.length < 2) {
                throw new ParseException("invalid argument");
            }
            String operation = all[1];
            String[] cmds = new String[0];
            return new ConvertCommand(new Transformation(operation, cmds));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConvertCommand.MESSAGE_USAGE), pe);
        }
    }
}
