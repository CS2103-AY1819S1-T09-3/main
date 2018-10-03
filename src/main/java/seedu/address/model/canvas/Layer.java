package seedu.address.model.canvas;

//@author Jeffry

import javafx.scene.image.Image;
import seedu.address.model.transformation.TransformationSet;

import static java.util.Objects.requireNonNull;
/**
 * Represents a layer in a canvas.
 * Guarantees that the image in the layer is not null.
 */

public class Layer {
    private final Image image;

    private int x, y;
    private int height, width;

    private TransformationSet history;

    public Layer(Image image){
        this.image = requireNonNull(image);
        this.x = 0;
        this.y = 0;
        this.height = (int)image.getHeight();
        this.width = (int)image.getWidth();
        history = new TransformationSet();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return String.format("-page %dx%d+%d+%d %s",height, width, x, y, image.getUrl());
    }
}
