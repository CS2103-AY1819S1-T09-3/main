package seedu.address.commons.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.canvas.Layer;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 * @author lancelotwillow
 */
public class ImageMagickUtil {
    //initialize the paths used in the imageMagic
    private static final int LINUX = 1;
    private static final int WINDOWS = 2;
    private static final int MAC = 3;
    private static String ectPath = "";
    private static String convertExecutablePath ="";
    private static String imageMagickPath = ImageMagickUtil.class.getResource("/imageMagic").getPath();
    private static String tmpPath = imageMagickPath + "/tmp";
    private static String commandSaveFolder;

    /**
     * get the path of the package location
     * @return
     * @throws NoSuchElementException
     */
    public static URL getImageMagickZipUrl() throws NoSuchElementException {
        switch (getPlatform()) {
        case MAC:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        case WINDOWS:
            return ImageMagickUtil.class.getResource("/imageMagic/package/win/ImageMagick-7.0.8-14.zip");
        default:
            return ImageMagickUtil.class.getResource("/imageMagic/package/mac/ImageMagick-7.0.8.zip");
        }
    }

    public static String getExecuteImageMagick() throws NoSuchElementException {
        if (ectPath != null) {
            return ectPath;
        }
        throw new NoSuchElementException("cannot find the file");
    }

    public static String getCommandSaveFolder() {
        return commandSaveFolder;
    }

    public static Path getTempFolderPath() {
        return Paths.get(tmpPath);
    }

    /**
     * get the platform;
     * @return
     */
    public static int getPlatform() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return WINDOWS;
        } else if (os.contains("mac")) {
            return MAC;
        } else if (os.contains("nux") || os.contains("ubuntu")) {
            return LINUX;
        }
        return 0;
    }

    /**
     * with a path and the transmission, return the bufferedimage processed.
     * @param path
     * @param transformation
     * @return
     * @throws ParseException
     * @throws IOException
     * @throws InterruptedException
     */
    public static BufferedImage processImage(Path path, Transformation transformation)
            throws ParseException, IOException, InterruptedException, IllegalArgumentException {
        ArrayList<String> cmds = parseArguments(transformation);
        String modifiedFile = tmpPath + "/output.png";
        //create a processbuilder to blur the image
        ArrayList<String> args = new ArrayList<>();
        args.add(ImageMagickUtil.getExecuteImageMagick());
        args.add(path.toAbsolutePath().toString());
        args.add("-background");
        args.add("rgba(0,0,0,0)"); //HARDFIX!
        args.add("-" + cmds.get(0));
        for (int i = 1; i < cmds.size(); i++) {
            args.add(cmds.get(i));
        }
        args.add(modifiedFile);
        return runProcessBuilder(args, modifiedFile);
    }

    /**
     * parse the argument passing to the imageMagic to check the validation about the arguments
     * @param transformation
     * @return
     * @throws IOException
     * @throws ParseException
     */
    private static ArrayList<String> parseArguments(Transformation transformation)
            throws IOException, ParseException, IllegalArgumentException {
        ArrayList<String> trans = transformation.toList();
        String operation = trans.get(0);
        if (!operation.startsWith("@")) {
            URL fileUrl = ImageMagickUtil.class.getResource("/imageMagic/commandTemplates/" + operation + ".json");
            if (fileUrl == null) {
                throw new ParseException("Invalid argument, cannot find");
            }
            List<String> cmds = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation, "arg");
            int num = cmds.size();
            String template = cmds.toString();
            if (num != trans.size() - 1) {
                throw new IllegalArgumentException("Invalid arguments, the arguments should be "
                        + operation + " " + template.substring(1, template.length() - 1));
            }
            List<String> patterns = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation, "pattern");
            for (int i = 0; i < patterns.size(); i++) {
                if (!trans.get(i + 1).matches(patterns.get(i))) {
                    throw new IllegalArgumentException("Invalid arguments, the arguments should be:"
                            + operation + " " + template.substring(1, template.length() - 1));
                }
            }
            return trans;
        } else {
            operation = operation.substring(1);
            File file = new File(commandSaveFolder + "/" + operation + ".json");
            if (!file.exists()) {
                throw new ParseException("Invalid argument, cannot find");
            }
            ArrayList<String> cmds = new ArrayList<>(JsonConvertArgsStorage.retrieveCommandArguments(file, operation));
            return cmds;
        }
    }

    /**
     * Given a list of arguments to ImageMagick, calls the actual ImageMagick executable with the output set to the
     * path provided
     * @param args An ArrayList of arguments, the first of which needs to be a legal ImageMagick executable.
     * @param output An URL to the output file.
     * @return
     * @throws IOException
     * @throws InterruptedException
     */

    public static BufferedImage runProcessBuilder(ArrayList<String> args, String output)
            throws IOException, InterruptedException, IllegalArgumentException {
        ProcessBuilder pb = new ProcessBuilder(args);
        if (getPlatform() == MAC) {
            Map<String, String> mp = pb.environment();
            mp.put("DYLD_LIBRARY_PATH", imageMagickPath + "/ImageMagick-7.0.8/lib/");
        }
        Process process = pb.start();
        process.waitFor();
        if (process.exitValue() != 0) {
            throw new IllegalArgumentException("the process can not be done! check the command entered");
        }
        List<String> tmp = pb.command();
        System.out.println(tmp);
        FileInputStream is = new FileInputStream(output);
        Image modifiedImage = new Image(is);
        return SwingFXUtils.fromFXImage(modifiedImage, null);
    }

    /**
     * copy the imageMagick outside of the jarfile in order to call it.
     * @author lancelotwillow
     * @param userPrefs
     * @throws IOException
     * @throws InterruptedException
     */
    public static void copyOutside(UserPrefs userPrefs) throws IOException, InterruptedException {
        URL zipUrl = getImageMagickZipUrl();
        Path currentPath = userPrefs.getCurrDirectory();
        File zipFile = new File(currentPath.toString() + "/temp.zip");
        File tempFolder = new File(userPrefs.getCurrDirectory() + "/tempFolder");
        tempFolder.mkdir();
        ResourceUtil.copyResourceFileOut(zipUrl, zipFile);
        switch (getPlatform()) {
        case MAC:
            Process process = new ProcessBuilder(
                    "tar", "zxvf", zipFile.getPath(), "-C", currentPath.toString()).start();
            process.waitFor();
            //remove the __MACOSX folder in the mac
            new ProcessBuilder("rm", "-rf", currentPath.toString() + "/__MACOSX").start();
            ectPath = currentPath.toString() + "/ImageMagick-7.0.8/bin/magick";
            convertExecutablePath = currentPath.toString() + "/ImageMagick-7.0.8/bin/convert";
            break;
        case WINDOWS:
            ResourceUtil.unzipFolder(zipFile);
            ectPath = currentPath.toString() + "/ImageMagick-7.0.8-14-portable-Q16-x64/magick.exe";
            convertExecutablePath = currentPath.toString() + "/ImageMagick-7.0.8-14-portable-Q16-x64/convert.exe";
            break;
        default:
        }
        imageMagickPath = currentPath.toString();
        tmpPath = tempFolder.getPath();
        commandSaveFolder = currentPath.toString() + "/PiconsoCommands";
        File commandFolder = new File(commandSaveFolder);
        if (!(commandFolder.exists() && commandFolder.isDirectory())) {
            commandFolder.mkdir();
        }
        zipFile.delete();
    }
    //@@author j-lum

    public static String getConvertExecutablePath() {
        if (convertExecutablePath != null) {
            return convertExecutablePath;
        }
        throw new NoSuchElementException("The ImageMagick binaries cannot be found!");
    }
    /**
     * Creates a ProcessBuilder instance to merge/flatten layers.
     * @param c - A canvas to be processed
     * @return a buffered image with a merged canvas.
     */
    public static BufferedImage processCanvas(Canvas c) throws IOException, InterruptedException {
        ArrayList<String> args = new ArrayList<>();
        String output = tmpPath + "/modified.png";
        args.add(getConvertExecutablePath());
        args.add("-size");
        args.add(String.format("%dx%d", c.getWidth(), c.getHeight()));
        args.add("-background");
        args.add(c.getBackgroundColor());
        for (Layer l: c.getLayers()) {
            args.add("-page");
            args.add(String.format("+%d+%d", l.getX(), l.getY()));
            args.add(String.format("%s", l.getImage().getCurrentPath()));
        }
        if (c.isCanvasAuto()) {
            args.add("-layers");
            args.add(" merge");
        } else {
            args.add("-flatten");
        }
        args.add(output);
        return runProcessBuilder(args, output);
    }

    /**
     * Given any canvas, renders it to the target panel.
     * @param c - Canvas to render
     * @param logger - an instance of the logger
     * @param target - the name of the ImagePanel to target
     */
    public static void render(Canvas c, Logger logger, String target) {
        try {
            EventsCenter.getInstance().post(
                    new ChangeImageEvent(
                            SwingFXUtils.toFXImage(
                                    ImageMagickUtil.processCanvas(c), null), target));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } catch (InterruptedException e) {
            logger.severe(e.getMessage());
        }

    }

}
