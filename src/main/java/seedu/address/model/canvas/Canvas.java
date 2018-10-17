package seedu.address.model.canvas;

import java.util.ArrayList;

import seedu.address.model.PreviewImage;


//@author Jeffry
/**
 * Represents a working canvas.
 * Guarantees that there is at least one layer remaining on the canvas
 * and that the canvas area must be larger than zero.
 */
public class Canvas {
    private static final String LAYER_NAME = "Layer %d";

    private ArrayList<Layer> layers = new ArrayList<>();
    private Boolean canvasIsAuto;

    public Canvas(){

    }

    public void addLayer(Layer l) {
        layers.add(l);
    }

    public void addLayer(PreviewImage i, String name) {
        layers.add(new Layer(i, String.format(LAYER_NAME, layers.size())));
    }
}
