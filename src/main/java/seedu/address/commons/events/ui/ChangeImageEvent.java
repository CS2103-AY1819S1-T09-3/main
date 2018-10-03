package seedu.address.commons.events.ui;

import javafx.scene.image.Image;
import seedu.address.commons.events.BaseEvent;

import static java.util.Objects.requireNonNull;

//@author Jeffry
/**
 * A simple event that notifies a target ImagePanel to change its image.
 */

public class ChangeImageEvent extends BaseEvent {

    public final Image image;
    public final String target;

    /**
     * Constructor for ChangeImageEvent
     * @param image Image to replace
     * @param target The name of the ImageView to target.
     */
    public ChangeImageEvent(Image image, String target) {
        this.image = requireNonNull(image);
        this.target = requireNonNull(target);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
