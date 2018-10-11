package seedu.address.model.transformation;

<<<<<<< HEAD
//@author Jeffry

import java.util.Arrays;
import java.util.List;

=======
//@@uthor j-lum
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
/**
 * Represents a single transformation to a single layer in a canvas.
 */
public class Transformation {

    private String operation;
    private String[] args;

    public Transformation(String operation, String... args) {
        this.operation = operation;
        this.args = args;
    }

<<<<<<< HEAD
    //@author tianyang
    /**
     * print out the transformation
     * @return
     */
=======
    //@@author lancelotwillow
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
    @Override
    public String toString() {
        String result = operation;
        for (int i = 0; i < args.length; i++) {
            result += "\n -" + args[i];
        }
        return result; //whoever is in charge do this
    }

    //@author tianyang
    /**
     * return a list of String that contains all the arguments and the operation
     * @return
     */
    public List<String> toList() {
        List<String> list = Arrays.asList(args);
        list.add(0, operation);
        return list;
    }
}
