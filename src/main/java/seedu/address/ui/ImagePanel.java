package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeImageEvent;

import java.util.logging.Logger;

/**
 * An UI component that displays an Image.
 */
public class ImagePanel extends UiPart<Region> {

    private static final String FXML = "ImagePanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    /**
     * An image panel for showing the previews
     */

    private final ObjectProperty<Image> image = new SimpleObjectProperty<Image>(new Image("https://via.placeholder.com/500x500"));
    private final String name;
    @FXML
    private ImageView imageView;

    public ImagePanel(String name) {
        super(FXML);
        this.name = name;
        imageView.imageProperty().bind(image);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePreviewImageEvent(ChangeImageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if(this.name.equals(event.target)){
            Platform.runLater(() -> image.setValue(event.image));
        }
    }

}
