package seedu.address.model;

import java.awt.image.BufferedImage;

import seedu.address.model.transformation.Transformation;
import seedu.address.model.transformation.TransformationSet;

//@author Ivan

/**
 * Wraps the image and transformation set for preview.
 */
public class PreviewImage implements PreviewableImage {
    private final BufferedImage image;
    private final TransformationSet transformationSet;


    public PreviewImage(BufferedImage image) {
        this.image = image;
        this.transformationSet = new TransformationSet();
    }

    public PreviewImage(PreviewableImage previewableImage) {
        this.image = previewableImage.getImage();
        this.transformationSet = previewableImage.getTransformationSet();
    }

    public PreviewImage(BufferedImage image, TransformationSet transformationSet) {
        this.image = image;
        this.transformationSet = transformationSet;
    }

    public BufferedImage getImage() {
        return image;
    }

    public TransformationSet getTransformationSet() {
        return transformationSet;
    }

    public void addTransformation(Transformation t) {
        transformationSet.addTransformations(t);
    }
}
