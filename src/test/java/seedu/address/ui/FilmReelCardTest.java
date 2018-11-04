package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalImages.IMAGE_LIST;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.MainApp;
/**
 * Tests for {@code FilmReelCard}
 * Display is tested in {@code FilmReelTest}
 */
public class FilmReelCardTest extends GuiUnitTest {
    private List<Path> paths = new ArrayList<>();

    @Before
    public void setUpResources() {
        for (String s : IMAGE_LIST) {
            String pathName = MainApp.class.getResource(s).getPath();
            Path path = Paths.get(pathName);
            assertNotNull(pathName + " does not exist.", path);
            paths.add(path);
        }
    }

    @Test
    public void equals() throws FileNotFoundException {

        FilmReelCard imageCard = new FilmReelCard(paths.get(0), 0);

        // same image, same index -> returns true
        FilmReelCard copy = new FilmReelCard(paths.get(0), 0);
        assertTrue(imageCard.equals(copy));

        // same object -> returns true
        assertTrue(imageCard.equals(imageCard));

        // null -> returns false
        assertFalse(imageCard.equals(null));

        // different types -> returns false
        assertFalse(imageCard.equals(0));

        // different image, same index -> returns false
        copy = new FilmReelCard(paths.get(0), 1);
        assertFalse(imageCard.equals(copy));

        // same person, different index -> returns false
        copy = new FilmReelCard(paths.get(1), 0);
        assertFalse(imageCard.equals(copy));
    }
}
