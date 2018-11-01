package seedu.address.model;

import static seedu.address.commons.core.Messages.MESSAGE_LOGIN_FAILURE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeDirectoryEvent;
import seedu.address.commons.events.ui.ChangeImageEvent;
import seedu.address.commons.events.ui.ClearHistoryEvent;
import seedu.address.commons.events.ui.TransformationEvent;
import seedu.address.commons.events.ui.UpdateFilmReelEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.canvas.Canvas;
import seedu.address.model.google.PhotoHandler;
import seedu.address.model.google.PhotosLibraryClientFactory;
import seedu.address.model.transformation.Transformation;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private List<Path> dirImageList;
    private Path currentOriginalImage;
    private PhotoHandler photoLibrary;
    private Canvas canvas;

    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(UserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs);

        logger.fine("Initializing with user prefs " + userPrefs);

        this.userPrefs = userPrefs;
        this.userPrefs.initImageList();
        dirImageList = this.userPrefs.getCurrImageListBatch();

        try {
            //photoLibrary = PhotosLibraryClientFactory.loginUserIfPossible();
        } catch (Exception e) {
            logger.warning("Unable to log into user account");
        }

    }

    public ModelManager() {
        this(new UserPrefs());
    }

    //=========== Directory Image List Accessors =============================================================
    // @@author benedictcss
    /**
     * Returns an array list of the images from the current directory {@code dirImageList}
     * backed by the list of {@code userPrefs}
     */
    @Override
    public List<Path> getDirectoryImageList() {
        this.dirImageList = userPrefs.getCurrImageListBatch();
        return this.dirImageList;
    }

    /**
     * Returns the total number of images in current directory
     */
    @Override
    public int getTotalImagesInDir() {
        return userPrefs.getTotalImagesInDir();
    }

    /**
     * Returns the current number of remaining pictures in {@code UserPrefs}
     */
    @Override
    public int numOfRemainingImagesInDir() {
        return userPrefs.numOfRemainingImagesInDir();
    }

    /**
     * Returns the current batch pointer in {@code UserPrefs}
     */
    @Override
    public int getCurrBatchPointer() {
        return userPrefs.getCurrBatchPointer();
    }

    /**
     * Updates the batch pointer in {@code UserPrefs}
     */
    @Override
    public void updateImageListNextBatch() {
        userPrefs.updateImageListNextBatch();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList(), true));
    }

    /**
     * Updates the batch pointer in {@code UserPrefs}
     */
    public void updateImageListPrevBatch() {
        userPrefs.updateImageListPrevBatch();
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList(), true));
    }

    /**
     * Remove image from {@code dirImageList} at the given {@code idx}
     */
    @Override
    public void removeImageFromList(int idx) {
        this.dirImageList.remove(idx);
    }

    @Override
    public Path getCurrentOriginalImage() {
        return this.currentOriginalImage;
    }

    /**
     * Update the current displayed original image and
     * reinitialize the previewImageManager with the new image
     */
    @Override
    public void updateCurrentOriginalImage(Image img, Path imgPath) {
        currentOriginalImage = imgPath;
        PreviewImage selectedImage = new PreviewImage(SwingFXUtils.fromFXImage(img, null));
        canvas = new Canvas(selectedImage);

        EventsCenter.getInstance().post(new ClearHistoryEvent());
    }

    /**
     * Update the current displayed original image and
     * reinitialize the previewImageManager with the new image, without imgPath
     */
    @Override
    public void updateCurrentOriginalImageForTest(PreviewImage previewImage) {
        canvas = new Canvas(previewImage);
    }
    //@@author

    //=========== GoogleClient Accessors =============================================================

    @Override
    public void setPhotoHandler(PhotoHandler instance) {
        photoLibrary = instance;
    }

    @Override
    public PhotoHandler getPhotoHandler() throws CommandException {
        if (photoLibrary == null) {
            try {
                photoLibrary = PhotosLibraryClientFactory.createClient();
            } catch (Exception e) {
                throw new CommandException(MESSAGE_LOGIN_FAILURE);
            }
        }
        return photoLibrary;
    }

    @Override
    public String getUserLoggedIn () throws CommandException {
        if (photoLibrary == null) {
            return null;
        }
        return getPhotoHandler().identifyUser();
    }

    //=========== Undo/Redo =================================================================================
    @Override
    public boolean canUndoPreviewImage() {
        return getCurrentPreviewImage().canUndo();
    }

    @Override
    public boolean canRedoPreviewImage() {
        return getCurrentPreviewImage().canRedo();
    }

    @Override
    public void undoPreviewImage() {
        getCurrentPreviewImage().undo();
        BufferedImage newImage = getCurrentPreviewImage().getImage();
        EventsCenter.getInstance().post(new TransformationEvent(true));
    }

    @Override
    public void redoPreviewImage() {
        getCurrentPreviewImage().redo();
        BufferedImage newImage = getCurrentPreviewImage().getImage();
        EventsCenter.getInstance().post(new TransformationEvent(false));
    }

    //=========== get/updating preview image ==========================================================================

    /**
     * Adds a transformation to current layer
     * @param transformation transformation to add
     * @throws ParseException
     * @throws InterruptedException
     * @throws IOException
     */
    public void addTransformation(Transformation transformation) throws
        ParseException, InterruptedException, IOException {
        canvas.getCurrentLayer().addTransformation(transformation);
        EventsCenter.getInstance().post(new TransformationEvent(transformation.toString()));
    }

    @Override
    public PreviewImage getCurrentPreviewImage() {
        return canvas.getCurrentLayer().getImage();
    }

    @Override
    public void setCurrentPreviewImage(PreviewImage previewImage) {
        setCurrentPreviewImage(previewImage);
    }

    @Override
    public Path getCurrentPreviewImagePath() {
        return getCurrentPreviewImage().getCurrentPath();
    }

    //@@author lancelotwillow
    @Override
    public void updateCurrentPreviewImage(BufferedImage image, Transformation transformation) {
        getCurrentPreviewImage().addTransformation(transformation);
        getCurrentPreviewImage().commit(image);
        EventsCenter.getInstance().post(
                new ChangeImageEvent(
                        SwingFXUtils.toFXImage(getCurrentPreviewImage().getImage(), null), "preview"));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs);
    }

    //=========== Update UserPrefs ==========================================================================

    // @@author benedictcss
    @Override
    public void updateCurrDirectory(Path newCurrDirectory) {
        this.userPrefs.updateUserPrefs(newCurrDirectory);
        EventsCenter.getInstance().post(new ChangeDirectoryEvent(getCurrDirectory().toString()));
        EventsCenter.getInstance().post(new UpdateFilmReelEvent(getDirectoryImageList(), true));
    }

    @Override
    public Path getCurrDirectory() {
        return this.userPrefs.getCurrDirectory();
    }

    //LOL
    public void testCache() {

    }


    //=========== Canvas and layers ==========================================================================

    public void addLayer(PreviewImage i) {
        canvas.addLayer(i);
    }

    /**
     * Overloads the addLayer function to handle an optional name.
     * @param i
     * @param name
     */
    public void addLayer(PreviewImage i, String name) {
        canvas.addLayer(i, name);

    }

    public void removeLayer(Index i) {
        canvas.removeLayer(i);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
