package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.google.GoogleClientInstance;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final PreviewImageManager previewImageManager;
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private GoogleClientInstance photoLibrary = null;
    private ArrayList<String> dirImageList;
    private BufferedImage currDisplayedPic;

    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        previewImageManager = PreviewImageManager.getInstance();
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        this.userPrefs = userPrefs;
        dirImageList = new ArrayList<>();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Directory Image List Accessors =============================================================

    /**
     * Returns an array list of the images from the current directory {@code dirImageList}
     * backed by the list of {@code userPrefs}
     */
    @Override
    public ArrayList<String> getDirectoryImageList() {
        this.dirImageList = userPrefs.getAllImages();
        return this.dirImageList;
    }

    /**
     * Returns an array list of the images from the current directory {@code dirImageList}
     * backed by the list of {@code userPrefs}
     */
    @Override
    public void updateImageList() {
        userPrefs.updateImageList();
    }

    /**
     * Remove image from {@code dirImageList} at the given {@code idx}
     */
    @Override
    public void removeImageFromList(int idx) {
        this.dirImageList.remove(idx);
    }

    @Override
    public BufferedImage getDisplayedImage() {
        return this.currDisplayedPic;
    }

    @Override
    public void updateCurrDisplayedImage(Image img) {
        currDisplayedPic = SwingFXUtils.fromFXImage(img, null);
    }

    //=========== GoogleClient Accessors =============================================================

    @Override
    public void setGoogleClientInstance(GoogleClientInstance instance) {
        photoLibrary = instance;
    }

    @Override
    public GoogleClientInstance getGoogleClientInstance() {
        return photoLibrary;
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoPreviewImageManager() {
        return previewImageManager.canUndo();
    }

    @Override
    public boolean canRedoPreviewImageManager() {
        return previewImageManager.canRedo();
    }

    @Override
    public void undoPreviewImageManager() {
        previewImageManager.undo();
        // indicateAddressBookChanged();
    }

    @Override
    public void redoPreviewImageManager() {
        previewImageManager.redo();
        // indicateAddressBookChanged();
    }

    @Override
    public void commitPreviewImageManager() {
        // TODO: previewImageManager.commit(editedImage);
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Update UserPrefs ==========================================================================

    @Override
    public void updateUserPrefs(Path newCurrDirectory) {
        this.userPrefs.updateCurrDirectory(newCurrDirectory);
    }

    @Override
    public Path getCurrDirectory() {
        return this.userPrefs.getCurrDirectory();
    }
}
