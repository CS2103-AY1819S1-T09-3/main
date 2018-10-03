package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.storage.JsonConvertArgsStorage;

import java.util.List;

public class CreateConvertCommand extends Command {
    private List<String> cmds;
    private String name;

    CreateConvertCommand(String name, List<String> cmds) {
        if(cmds.isEmpty()) {
            throw new IllegalArgumentException("nothing inside the command arguments");
        } else {
            this.cmds = cmds;
            this.name = name;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        JsonConvertArgsStorage.storeArgument(name, cmds);
        return null;
    }
}
