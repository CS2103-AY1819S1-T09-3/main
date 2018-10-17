package seedu.address.model.canvas;

//@author Jeffry
import static java.util.Objects.requireNonNull;

import seedu.address.model.PreviewImage;

/**
 * Represents a layer in a canvas.
 * Guarantees that the image in the layer is not null.
 */

public class Layer {
    private final PreviewImage image;
    private int x;
    private int y;
    private int height;
    private int width;
    private String name;

    public Layer(PreviewImage image, String name) {
        this.image = requireNonNull(image);
        this.x = 0;
        this.y = 0;
        this.height = image.getImage().getHeight();
        this.width = image.getImage().getWidth();
        this.name = name;
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
        //return String.format("-page %dx%d+%d+%d %s", height, width, x, y, image.getUrl());
        // TODO : keep track of the path in PreviewImage
        return "";
    }
}
