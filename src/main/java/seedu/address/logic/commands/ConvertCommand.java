package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.net.URL;

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
    private URL fileUrl;
    private Transformation transformation;
    /**
     * the constructor take the path of the JSON file of the detail of the convert operation
     * @param fileUrl the path to the JSON file
     * @param transformation contains the operation to be processed to the image
     */
    public ConvertCommand(URL fileUrl, Transformation transformation) throws ParseException {
        if (!isFileExist(fileUrl)) {
            throw new ParseException("no file found");
        }
        this.fileUrl = fileUrl;
        this.transformation = transformation;
    }

    private static boolean isFileExist(URL fileUrl) {
        /*return new File(fileUrl.toString()).exists();*/
        return true;
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
            model.addTransformation(transformation);
            BufferedImage modifiedImage = ImageMagickUtil.processImage(model.getCurrentPreviewImagePath(),
                    transformation);
            model.updateCurrentPreviewImage(modifiedImage, transformation);
        } catch (Exception e) {
            throw new CommandException(e.toString());
        }
        return new CommandResult("process is done");
    }
}
