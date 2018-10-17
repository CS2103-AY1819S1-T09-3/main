package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_GOOGLE_INVALID_FORMAT;

import seedu.address.logic.commands.google.GoogleCommand;
import seedu.address.logic.commands.google.GoogleLsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author chivent

/**
 * Parses input arguments and creates the corresponding Google type command object
 */
public class GoogleCommandParser {

    private String type = "";

    /**
     * Parses the given {@code String} of arguments in the context of the GoogleCommand
     * and returns an GoogleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform an expected format
     */
    public GoogleCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String commandParam = parseArgumentString(args);

        //TODO: add upload, download command
        switch (type) {
        case "ls": {
            return new GoogleLsCommand(commandParam);
        }
        default:
            throw new ParseException(MESSAGE_GOOGLE_INVALID_FORMAT);
        }
    }

    /**
     * Parse argument string to extract type of command and arguments
     *
     * @param args command string entered by user
     * @return parameter to be used in Google-type Command
     * @throws ParseException if command format is wrong
     */
    private String parseArgumentString(String args) throws ParseException {

        String[] parameters = args.trim().split(" ", 2);

        // checking if argument string is a valid format
        if (parameters.length > 0) {
            type = parameters[0];

            // if command has extra arguments
            if (parameters.length > 1) {
                return parameters[1].trim();
            } else {
                return "";
            }
        } else {
            throw new ParseException(MESSAGE_GOOGLE_INVALID_FORMAT);
        }
    }
}
