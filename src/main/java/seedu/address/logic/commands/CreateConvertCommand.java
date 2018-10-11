package seedu.address.logic.commands;

<<<<<<< HEAD
import java.util.Iterator;
=======
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.JsonConvertArgsStorage;

/**
<<<<<<< HEAD
 * the class is the command that create a new convert command
=======
 * @author Zhang Tianyang
 * the class to create the convert command
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
 */
public class CreateConvertCommand extends Command {
    private List<String> cmds;
    private String name;

    CreateConvertCommand(String name, List<String> cmds) {
        if (cmds.isEmpty()) {
            throw new IllegalArgumentException("nothing inside the command arguments");
        } else {
            this.cmds = cmds;
            this.name = name;
        }
    }

    /**
     * to check whether the single argument tag is valid or not
     * @param arg
     * @throws IllegalArgumentException
     */
    private void checkSigleValidation(String arg) throws IllegalArgumentException {
        //just a template, not only this
        if (arg == "") {
            throw new IllegalArgumentException();
        }
    };


    /**
     * to check the validation of the whole argument list
     */
    private void checkValidation() {
        Iterator<String> iter = cmds.iterator();
        while (iter.hasNext()) {
            try {
                checkSigleValidation(iter.next());
            } catch (IllegalArgumentException e) {
                System.err.println(e);
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        JsonConvertArgsStorage.storeArgument(name, cmds);
        return null;
    }
}

