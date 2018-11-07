package seedu.address.ui;

//@@author chivent
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Test;

import guitests.guihandles.HistoryListPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.AllTransformationEvent;
import seedu.address.commons.events.ui.ClearHistoryEvent;
import seedu.address.commons.events.ui.TransformationEvent;

/**
 * Contains tests for editing list of {@code HistoryListPanel}.
 */
public class HistoryListPanelTest extends GuiUnitTest {

    private static final ClearHistoryEvent CLEAR_HISTORY_EVENT = new ClearHistoryEvent();

    private static final ArrayList<String> TRANSFORMATION_SAMPLE = new ArrayList<>(
            Arrays.asList("first", "second", "third", "fourth"));

    private static List<String> addSample = new ArrayList<>(
            Arrays.asList("new 1", "new 2", "new 3", "new 4", "new 5", "new 6"));

    private static ObservableList<String> sampleList = FXCollections.observableList(new ArrayList<>());

    private HistoryListPanelHandle historyListPanelHandle;
    private Queue<String> redoQueue = new LinkedList<>();

    @Test
    public void add() {
        initUi();

        // Assert regular add
        assertAddItem(addSample.get(0));

        // Assert add after add
        assertAddItem(addSample.get(1));

        // Assert add after undo
        assertAddItemAfterUndo(addSample.get(2));

        // Assert redo after added
        redoItem();
        assertAddItem(addSample.get(3));

        System.out.println(sampleList.size());
        // Assert add after undo and redo
        undoItem();
        redoItem();
        assertAddItem(addSample.get(4));

        // Assert add after undo x2
        undoItem();
        undoItem();
        assertAddItemAfterUndo(addSample.get(5));
        System.out.println(sampleList.size());
    }

    @Test
    public void undo() {
        initUi();

        System.out.println(sampleList.size());
        // Assert regular undo
        assertUndoItem();

        // Assert undo after undo
        assertUndoItem();

        // Assert undo after add
        redoQueue.clear();
        addItem(addSample.get(0));
        assertUndoItem();

        // Assert undo after redo
        redoItem();
        assertUndoItem();

        // Assert undo after undo & redo x 2
        undoItem();
        redoItem();
        redoItem();
        assertUndoItem();

        // Assert undo after undo, add, then redo (no undone expected to be appended to queue)
        addItemAfterUndo(addSample.get(1));
        redoItem();
        assertUndoItem();
    }

    @Test
    public void redo() {
        initUi();

        // Assert regular undo
        assertRedoItem();

        // Assert x2 redo after x2 undo
        undoItem();
        undoItem();
        assertRedoItem();
        assertRedoItem();

        // Assert redo after x2 undo
        undoItem();
        undoItem();
        assertRedoItem();

        // Assert redo after add after undo (no undone expected to be appended to queue)
        addItemAfterUndo(addSample.get(0));
        assertRedoItem();

        // Assert redo after add
        addItem(addSample.get(1));
        assertRedoItem();

        // Assert redo x2 after undo
        undoItem();
        assertRedoItem();
        assertRedoItem();
    }

    @Test
    public void handleClearHistoryEvent() {
//        initUi();
//        postNow(CLEAR_HISTORY_EVENT);
//        sampleList.clear();
//        assertListMatch();
//        assertEquals(0, historyListPanelHandle.getItems().size());
    }


    @Test
    public void handleAllTransformationEvent() {
        initUi();
        postNow(new AllTransformationEvent(true));
        sampleList.clear();
        assertListMatch();

        postNow(new AllTransformationEvent(false));
        sampleList.addAll(TRANSFORMATION_SAMPLE);
        assertListMatch();


    }

    /**
     * Add an item to {@code HistoryListView} and {@code sampleList}, clear the cached undo commands.
     */
    private void addItemAfterUndo(String item) {
        undoItem();
        redoQueue.clear();
        addItem(item);
    }

    /**
     * Adds item and asserts to check if added correctly
     */
    private void assertAddItemAfterUndo(String item) {
        addItemAfterUndo(item);
        assertListMatch();
    }

    /**
     * Restore item to {@code HistoryListView} and {@code sampleList}
     */
    private void redoItem() {
        if (redoQueue.size() > 0) {
            sampleList.add(redoQueue.poll());
        }
        postNow(new TransformationEvent(false));
    }

    /**
     * Restores item and asserts to check if added correctly
     */
    private void assertRedoItem() {
        redoItem();
        assertListMatch();
    }

    /**
     * Removes item from {@code HistoryListView} and {@code sampleList}
     */
    private void undoItem() {
        redoQueue.add(sampleList.get(sampleList.size() - 1));
        sampleList.remove(sampleList.size() - 1, sampleList.size());
        postNow(new TransformationEvent(true));
    }

    /**
     * Removes item and asserts to check if added correctly
     */
    private void assertUndoItem() {
        undoItem();
        assertListMatch();
    }

    /**
     * Adds item to {@code HistoryListView} and {@code sampleList}
     */
    private void addItem(String item) {
        sampleList.add(item);
        postNow(new TransformationEvent(item));
    }

    /**
     * Adds item and asserts to check if added correctly
     */
    private void assertAddItem(String item) {
        addItem(item);
        assertListMatch();
    }

    /**
     * Ensures that the lists from {@code historyListView} and {@code sampleList} match after changes
     */
    private void assertListMatch() {
        assertEquals(sampleList, historyListPanelHandle.getItems());
    }

    /**
     * Initializes {@code historyListPanelHandle} with a {@code HistoryListPanel} and fills {@code historyListView}
     */
    private void initUi() {
        sampleList.clear();
        sampleList.addAll(TRANSFORMATION_SAMPLE);
        redoQueue.clear();
        HistoryListPanel historyListPanel = new HistoryListPanel();
        uiPartRule.setUiPart(historyListPanel);
        historyListPanelHandle = new HistoryListPanelHandle(historyListPanel.getRoot());

        for (String item : sampleList) {
            postNow(new TransformationEvent(item));
        }
    }
}
