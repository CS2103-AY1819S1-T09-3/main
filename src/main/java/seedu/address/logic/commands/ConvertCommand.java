package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;


/**
 * @@author lancelotwillow
 * the class to execute the convert command that do the modification of the image
 */
public class ConvertCommand extends Command {

    public static final String COMMAND_WORD = "convert";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": do the operation to the image.\n"
            + "Parameters: operationName argument1 argument2 ...\n"
            + "Example: " + COMMAND_WORD + " blur 1x8";
    //the path of the json file containing the arguments of the convert command
    public static final URL SINGLE_COMMAND_TEMPLATE_PATH =
            ImageMagickUtil.class.getResource("/imageMagic/commandTemplates");
    private static final Logger logger = LogsCenter.getLogger(ConvertCommand.class);

    private Transformation transformation;


    /**
     * the constructor take the path of the JSON file of the detail of the convert operation
     * @param transformation contains the operation to be processed to the image
     */
    public ConvertCommand(Transformation transformation) throws ParseException {
        this.transformation = transformation;
    }

    /**
     * build a new processbuilder and initialize witht the commands need to the convert command
     * @param model {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        try {
            BufferedImage modifiedImage = ImageMagickUtil.processImage(model.getCurrentPreviewImagePath(),
                    transformation);
            model.updateCurrentPreviewImage(modifiedImage, transformation);
            ImageMagickUtil.render(model.getCanvas(), logger, "preview");
            model.addTransformation(transformation);
        } catch (Exception e) {
            throw new CommandException(e.toString());
        }
        return new CommandResult("process is done");
    }

    @Override
    public boolean equals(Object object) {
        ConvertCommand command = (ConvertCommand) object;
        return command == this || transformation.equals(command.transformation);
    }
}
