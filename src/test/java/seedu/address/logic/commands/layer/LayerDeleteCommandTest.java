package seedu.address.logic.commands.layer;

import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.canvas.Canvas;
import seedu.address.testutil.ModelGenerator;

class LayerDeleteCommandTest {

    @Test
    void execute_deleteValid_success() {
        String args = "2";
        Index indexToDelete = Index.fromOneBased(Integer.parseInt(args));
        Index workingOn = Index.fromOneBased(3);
        Index expected = Index.fromOneBased(2);
        Model model = ModelGenerator.getModelWithPopulatedCanvas();
        Model expectedModel = ModelGenerator.getModelWithPopulatedCanvas();
        model.getCanvas().setCurrentLayer(workingOn);
        try {
            expectedModel.getCanvas().removeLayer(indexToDelete);
        } catch (Exception e) {
            assertNull(e);
        }
        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerDeleteCommand(args),
                model,
                ch,
                String.format(LayerDeleteCommand.OUTPUT_SUCCESS, expected.getOneBased()),
                expectedModel);
    }

    @Test
    void execute_deleteCurrent_success() {
        String args = "2";
        Index workingOn = Index.fromOneBased(Integer.parseInt(args));
        Model model = ModelGenerator.getModelWithPopulatedCanvas();
        Model expectedModel = ModelGenerator.getModelWithPopulatedCanvas();
        model.getCanvas().setCurrentLayer(workingOn);

        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerDeleteCommand(args),
                model,
                ch,
                String.format(Canvas.OUTPUT_ERROR_CURRENT_LAYER),
                expectedModel);
    }

    @Test
    void execute_deleteOnly_success() {
        String args = "1";
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();

        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerDeleteCommand(args),
                model,
                ch,
                String.format(Canvas.OUTPUT_ERROR_ONLY_LAYER),
                expectedModel);
    }

    @Test
    void execute_deleteNull_success() {
        String args = null;
        Model model = ModelGenerator.getDefaultModel();
        Model expectedModel = ModelGenerator.getDefaultModel();

        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerDeleteCommand(args),
                model,
                ch,
                String.format(LayerDeleteCommand.OUTPUT_FAILURE),
                expectedModel);
    }

    @Test
    void execute_deleteInvalidIndex_success() {
        String args = "9";
        Index workingOn = Index.fromOneBased(Integer.parseInt(args));
        Model model = ModelGenerator.getModelWithPopulatedCanvas();

        CommandHistory ch = new CommandHistory();
        assertCommandSuccess(
                new LayerDeleteCommand(args),
                model,
                ch,
                String.format(LayerDeleteCommand.OUTPUT_FAILURE),
                model);
    }
}