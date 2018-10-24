package seedu.address.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.google.PhotoHandler;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Get PhotoHandler, directs user to login if yet to be logged in.
     */
    PhotoHandler getPhotoHandler() throws CommandException;

    /**
     * Set PhotoHandler.
     */
    void setPhotoHandler(PhotoHandler instance);

    /**
     * Gets currently logged in user
     */
    String getUserLoggedIn() throws CommandException;

    /**
     * Returns true if the model has previous PreviewImage states to restore.
     */
    boolean canUndoPreviewImageManager();

    /**
     * Returns true if the model has undone PreviewImage states to restore.
     */
    boolean canRedoPreviewImageManager();

    /**
     * Restores the model's PreviewImage to its previous state.
     */
    void undoPreviewImageManager();

    /**
     * Restores the model's PreviewImage to its previously undone state.
     */
    void redoPreviewImageManager();

    /**
     * Saves the current PreviewImage state for undo/redo.
     */
    void commitPreviewImageManager();

    /**
     * Updates the userPrefs current directory.
     */
    void updateCurrDirectory(Path newCurrDirectory);

    /**
     * Retrieves the userPrefs current directory.
     */
    Path getCurrDirectory();

    /**
     * Retrieves the list of images in current directory.
     */
    ArrayList<Path> getDirectoryImageList();

    /**
     * Updates the list of images in current directory.
     */
    void updateImageList();

    /**
     * Updates the list of images with the current image list.
     */
    void updateImageList(ArrayList<Path> dirImageList);

    /**
     * Removes the image of the given index in the list.
     */
    void removeImageFromList(int idx);

    /**
     * Retrieves the current displayed original image.
     */
    Path getCurrentOriginalImage();

    /**
     * Retrieves the current displayed preview image.
     */
    Path getCurrentPreviewImage();

    /**
     * Update the current displayed original image.
     */
    void updateCurrentOriginalImage(Image img, Path imgPath);
}
