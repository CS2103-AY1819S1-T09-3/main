package seedu.address.model.transformation;

import java.util.ArrayList;
import java.util.LinkedList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ImageMagickUtil;

<<<<<<< HEAD

=======
//@@author j-lum
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b

/**
 * Represents a set of transformations that can be applied to any layer.
 */
public class TransformationSet {
    private LinkedList<Transformation> transformations;
    private Index cursor;

    /**
     * Default constructor.
     */
    public TransformationSet() {
        transformations = new LinkedList<>();
        cursor = Index.fromZeroBased(0);
    }

    /**
     *
     */
    public TransformationSet(LinkedList<Transformation> transformations) {
        this.transformations = transformations;
        cursor = Index.fromOneBased(transformations.size());
    }

    /**
<<<<<<< HEAD
     * .
     * @param t
=======
     * Adds transformations to transformation set
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
     */
    public void addTransformations(Transformation t) {
        transformations.addLast(t);
        cursor = Index.fromOneBased(transformations.size());
    }

    /**
<<<<<<< HEAD
     * .
     * @param steps
=======
     * Removes transformations from transformation set
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
     */
    public void removeTransformations(int steps) {
        if (steps > transformations.size()) {
            throw new IndexOutOfBoundsException("TODO: refactor message somewhere else");
        }
        for (int i = 0; i < steps; i++) {
            transformations.pollLast();
        }
        cursor = Index.fromOneBased(transformations.size());
    }

    /**
     * Gathers all the transformations.
     *
     * @return a ProcessBuilder instance that will can be used to call ImageMagick
     */
    public ProcessBuilder toProcessBuilder() {
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add(ImageMagickUtil.getImageMagickPath());
        for (Transformation t : transformations) {
            arguments.add(t.toString());
        }
        return new ProcessBuilder(arguments);
    }
}
