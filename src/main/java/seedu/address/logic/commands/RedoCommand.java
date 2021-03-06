package seedu.address.logic.commands;

//@@author ihwk1996
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s current layer's previewImage to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Transformation successfully redone";
    public static final String MESSAGE_FAILURE = "No more transformations to redo";

    private static final Logger logger = LogsCenter.getLogger(RedoCommand.class);

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoPreviewImage()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoPreviewImage();
        ImageMagickUtil.render(model.getCanvas(), logger, "preview");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
