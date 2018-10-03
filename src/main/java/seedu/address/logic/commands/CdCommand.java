package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Path;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;



/**
 * Changes the current directory.
 */
public class CdCommand extends Command {

    public static final String COMMAND_WORD = "cd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the current directory.\n"
            + "Parameters: FILEPATH (existing directory)\n"
            + "Example: " + COMMAND_WORD + " Desktop/piconso";

    public static final String MESSAGE_FAILURE = "The system cannot find the path specified.";

    private final Path toDirectories;

    public CdCommand(Path directories) {
        this.toDirectories = directories;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        String currDirectory = model.getCurrDirectory().toString();
        String newDir = currDirectory + "\\" + toDirectories.toString();

        File dir = new File(newDir);
        if (!dir.isDirectory()) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        Path newCurrDirectory = dir.toPath().normalize();
        model.updateUserPrefs(newCurrDirectory);
        return new CommandResult(newCurrDirectory.toString());

    }
}
