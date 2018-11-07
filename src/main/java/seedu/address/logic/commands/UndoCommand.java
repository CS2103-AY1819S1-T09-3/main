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
 * Reverts the {@code model}'s current layer's previewImage to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Transformation successfully undone";
    public static final String MESSAGE_FAILURE = "No more transformations to undo";
    private static final Logger logger = LogsCenter.getLogger(UndoCommand.class);

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoPreviewImage()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoPreviewImage();
        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
