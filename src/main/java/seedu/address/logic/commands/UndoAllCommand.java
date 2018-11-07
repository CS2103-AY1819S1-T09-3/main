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
 * Reverts the {@code model}'s previewImageManager to its previous state.
 */
public class UndoAllCommand extends Command {

    public static final String COMMAND_WORD = "undo-all";
    public static final String MESSAGE_SUCCESS = "All transformations successfully undone";
    public static final String MESSAGE_FAILURE = "No more transformations to undo";
    private static final Logger logger = LogsCenter.getLogger(UndoAllCommand.class);

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoPreviewImage()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoAllPreviewImage();
        ImageMagickUtil.render(model.getCanvas(), logger, "preview");

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
