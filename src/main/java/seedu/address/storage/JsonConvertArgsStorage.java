package seedu.address.storage;

import java.nio.file.Path;
import java.util.List;

/**
<<<<<<< HEAD
 * @author Zhang Tianyang
 * this class is to convert the joson file that store the convert arguments to list<@code String>, and store the convert
=======
 * @@author lancelotwillow
 * this class is to convert the joson file that store the convert arguments to list[String], and store the convert
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b
 * command arguments insde the json file
 */
public class JsonConvertArgsStorage {

    public static void storeArgument(String name, List<String> cmds) {
    }

    public static List<String> retrieveArguments(Path filepath) {
        return null;
    }

}
