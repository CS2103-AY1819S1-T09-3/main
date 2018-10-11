package seedu.address.model.canvas;

//@author Jeffry
import static java.util.Objects.requireNonNull;

import javafx.scene.image.Image;
import seedu.address.model.transformation.TransformationSet;

<<<<<<< HEAD

=======
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
/**
 * Represents a layer in a canvas.
 * Guarantees that the image in the layer is not null.
 */

public class Layer {
    private final Image image;
<<<<<<< HEAD
=======

>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
    private int x;
    private int y;
    private int height;
    private int width;
<<<<<<< HEAD

    private TransformationSet history;

    public Layer(Image image) {
=======
    private String name;
    private TransformationSet history;

    public Layer(Image image, String name) {
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
        this.image = requireNonNull(image);
        this.x = 0;
        this.y = 0;
        this.height = (int) image.getHeight();
        this.width = (int) image.getWidth();
<<<<<<< HEAD
=======
        this.name = name;
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
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
        return String.format("-page %dx%d+%d+%d %s", height, width, x, y, image.getUrl());
    }
}
