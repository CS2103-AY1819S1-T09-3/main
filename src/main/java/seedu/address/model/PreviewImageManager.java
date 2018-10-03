package seedu.address.model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

//@author Ivan

/**
 * Singleton that keeps track of the history of PreviewImages.
 */
public class PreviewImageManager {

    // static variable single_instance of type Singleton
    private static PreviewImageManager single_instance = null;
    private List<PreviewImage> previewImageStateList;
    private int currentStatePointer;

    // private constructor
    private PreviewImageManager() {
        previewImageStateList = new ArrayList<>();
        previewImageStateList.add(new PreviewImage(new Image("https://via.placeholder.com/500x500")));
        currentStatePointer = 0;
    }

    // static method to create instance of PreviewImageManager class
    public static PreviewImageManager getInstance()
    {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new PreviewImageManager();
        }
        return single_instance;
    }

    //initialise with Image
    public void initialiseWithImage(PreviewImage initialImage) {
        previewImageStateList = new ArrayList<>();
        previewImageStateList.add(initialImage);
        currentStatePointer = 0;
    }

    public PreviewImage getCurrentPreviewImageState() {
        return previewImageStateList.get(currentStatePointer);
    }

    /**
     * Saves a copy of the latest {@code PreviewImage} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit(PreviewImage previewImage) {
        removeStatesAfterCurrentPointer();
        previewImageStateList.add(previewImage);
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        previewImageStateList.subList(currentStatePointer + 1, previewImageStateList.size()).clear();
    }

    /**
     * Restores the preview image to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
    }

    /**
     * Restores the preview image to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
    }

    /**
     * Returns true if {@code undo()} has preview image states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has preview image states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < previewImageStateList.size() - 1;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of previewImageState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of previewImageState list, unable to redo.");
        }
    }
}
