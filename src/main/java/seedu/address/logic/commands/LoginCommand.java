package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.google.GoogleClientInstance;
import seedu.address.model.google.PhotosLibraryClientFactory;

import static java.util.Objects.requireNonNull;

//TODO: add for parser

/**
 *  Logs in user
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Re-directs user to log into Google Photos. ";

    private static final String MESSAGE_SUCCESS = "Successfully logged in as %s";
    private static final String MESSAGE_LOGGED_IN = "Already logged in as %s.";
    private static final String MESSAGE_FAILURE = "Login unsuccessful";


    /**
     * Creates a LoginCommand to allow user to login to Google Account
     */
    public LoginCommand() {

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        GoogleClientInstance client = null;
        try {
            if (model.getGoogleClientInstance() == null) {
                model.setGoogleClientInstance(PhotosLibraryClientFactory.createClient());
                return new CommandResult(String.format(MESSAGE_SUCCESS, model.getGoogleClientInstance().identifyUser()));
            } else {
                return new CommandResult(String.format(MESSAGE_LOGGED_IN, model.getGoogleClientInstance().identifyUser()));
            }

        } catch (Exception ex) {
            return new CommandResult(String.format(MESSAGE_FAILURE));
        }
    }
}
