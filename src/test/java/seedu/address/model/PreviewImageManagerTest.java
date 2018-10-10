package seedu.address.model;

// import org.junit.Test;
// import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

public class PreviewImageManagerTest {

    private final PreviewImageManager previewImageManager = PreviewImageManager.getInstance();

    /**
     * Asserts that {@code previewImageManager} is currently pointing at {@code expectedCurrentState},
     * states before {@code previewImageManager#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code previewImageManager#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertPreviewImageManagerStatus(PreviewImageManager previewImageManager,
                                             List<PreviewImage> expectedStatesBeforePointer,
                                             PreviewImage expectedCurrentState,
                                             List<PreviewImage> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(previewImageManager.getCurrentPreviewImageState(), expectedCurrentState);

        // shift pointer to start of state list
        while (previewImageManager.canUndo()) {
            previewImageManager.undo();
        }

        // check states before pointer are correct
        for (PreviewImage expectedPreviewImage : expectedStatesBeforePointer) {
            assertEquals(expectedPreviewImage, previewImageManager.getCurrentPreviewImageState());
            previewImageManager.redo();
        }

        // check states after pointer are correct
        for (PreviewImage expectedPreviewImage : expectedStatesAfterPointer) {
            previewImageManager.redo();
            assertEquals(expectedPreviewImage, previewImageManager.getCurrentPreviewImageState());
        }

        // check that there are no more states after pointer
        assertFalse(previewImageManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> previewImageManager.undo());
    }

    /**
     * Shifts the {@code previewImageManager#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(PreviewImageManager previewImageManager, int count) {
        for (int i = 0; i < count; i++) {
            previewImageManager.undo();
        }
    }
}
