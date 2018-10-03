package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.JsonConvertArgsStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Zhang Tianyang
 * the class to execute the convert command that do the modification of the image
 */
public class ConvertCommand extends Command {

    //the path of the json file containing the arguments of the convert command
    private Path filepath;

    /**
     * the constructor take the path of the JSON file of the detail of the convert operation
     * @param filepath the path to the JSON file
     */
    ConvertCommand(Path filepath) {
        this.filepath = filepath;
        if(!isFileExist(filepath)) {
            throw new IllegalArgumentException("no file found");
        }
    }

    static private boolean isFileExist(Path filepath) {
        return new File(filepath.toString()).exists();
    }

    private List<String> parseArguments() {
        List<String> cmds = JsonConvertArgsStorage.retrieveArguments(filepath);
        return cmds;
    }

    /**
     * @author Zhang Tianyang
     * build a new processbuilder and initialize witht the commands need to the convert command
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        ProcessBuilder pb = new ProcessBuilder(parseArguments());
        try {
            pb.start();
        } catch(IOException e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
