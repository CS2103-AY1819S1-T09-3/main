package seedu.address.model;

import javafx.scene.image.Image;
import seedu.address.model.transformation.TransformationSet;

//@author Ivan

/**
 * Wraps the image and transformation set for preview.
 */
public class PreviewImage implements PreviewableImage {
    private final Image image;
    private final TransformationSet transformationSet;

    public PreviewImage(Image image, TransformationSet transformationSet) {
        this.image = image;
        this.transformationSet = transformationSet;
    }

    public PreviewImage(Image image) {
        this.image = image;
        this.transformationSet = new TransformationSet();
    }

    public PreviewImage(PreviewableImage previewableImage) {
        this.image = previewableImage.getImage();
        this.transformationSet = previewableImage.getTransformationSet();
    }

    public PreviewImage() {
        this.image = new Image("https://via.placeholder.com/500x500");
        this.transformationSet = new TransformationSet();
    }

    public Image getImage() {
        return image;
    }

    public TransformationSet getTransformationSet() {
        return transformationSet;
    }
}
