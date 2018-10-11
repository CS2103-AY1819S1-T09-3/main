package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import seedu.address.commons.events.BaseEvent;

<<<<<<< HEAD
//@author Jeffry
=======

//@author j-lum
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
/**
 * A simple event that notifies a target ImagePanel to change its image.
 */

public class ChangeImageEvent extends BaseEvent {

    public final Image image;
    public final String target;

    /**
     * Constructor for ChangeImageEvent
     *
     * @param image  Image to replace
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
