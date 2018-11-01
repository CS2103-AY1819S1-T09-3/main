package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.events.BaseEvent;

//@@author chivent
/**
 * An event that updates the list of images in film reel upon call events.
 */
public class UpdateFilmReelEvent extends BaseEvent {

    public final List<Path> paths;

    /**
     * True if command is cd or next or prev, else false.
     */
    public final boolean refreshSelection;

    /**
     * Constructor for LoginStatusEvent
     *
     * @param paths List of paths to new images
     */
    public UpdateFilmReelEvent(List<Path> paths, boolean refresh) {
        this.refreshSelection = refresh;
        this.paths = requireNonNull(paths);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
