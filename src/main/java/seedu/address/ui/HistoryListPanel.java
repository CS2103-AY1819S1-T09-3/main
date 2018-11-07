package seedu.address.ui;

//@@author chivent

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.*;


/**
 * Panel containing the list of past transformations.
 */
public class HistoryListPanel extends UiPart<Region> {
    public static final String SELECTED_STYLE = "-fx-background-color: #a3a3a3;  -fx-text-fill: #1f1f1f;";

    private static final String FXML = "HistoryListPanel.fxml";
    private static final String DEFAULT_STYLE = "-fx-background-color: transparent;  -fx-text-fill: #6e6e6e;";

    private final Logger logger = LogsCenter.getLogger(getClass());
    //private ObservableList<String> items = FXCollections.observableArrayList();

    private ArrayList<ObservableList<String>> itemsArray = new ArrayList<>();
    private int currentLayer = 1;

    /**
     * Stores transformations that have been undone.
     */
    //private Queue<String> redoQueue = new LinkedList<>();
    private ArrayList<Queue<String>> redoQueueArray = new ArrayList<>();

    @FXML
    private ListView<String> historyListView;

    @FXML
    private Text historyTitle;

    public HistoryListPanel() {
        super(FXML);
        historyTitle.setText("T.History @ Layer-" + currentLayer);
        itemsArray.add(FXCollections.observableArrayList()); // index 0 (filler)
        itemsArray.add(FXCollections.observableArrayList()); // index 1
        redoQueueArray.add(new LinkedList<>()); // index 0 (filler)
        redoQueueArray.add(new LinkedList<>()); // index 1
        historyListView.setItems(itemsArray.get(currentLayer));
        historyListView.setCellFactory(listView -> new HistoryListPanel.HistoryListViewCell());
        registerAsAnEventHandler(this);
    }

    private ObservableList<String> getCurrentLayerList() {
        return itemsArray.get(currentLayer);
    }

    private Queue<String> getCurrentRedoQueue() {
        return redoQueueArray.get(currentLayer);
    }

    /**
     * Event that triggers alteration of panel, called upon undo, redo and add transformation
     * Undo -> Removes most recent transformation, stores it in case of undo
     * Redo -> Restores most recently removed transformation
     * Add -> Adds new transformation to display
     *
     * @param event
     */
    @Subscribe
    private void handleTransformationEvent(TransformationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ObservableList<String> currentLayerList = getCurrentLayerList();
        Queue<String> currentRedoQueue = getCurrentRedoQueue();

        if (event.isRemove) {
            //undo command
            if (getCurrentLayerList().size() > 0) {
                currentRedoQueue.add(currentLayerList.get(getCurrentLayerList().size() - 1));
                currentLayerList.remove(currentLayerList.size() - 1, currentLayerList.size());
            }
        } else {
            if (event.transformation.isEmpty()) {
                //redo command
                if (currentRedoQueue.size() > 0) {
                    currentLayerList.add(currentRedoQueue.poll());
                }
            } else {
                //add command
                currentLayerList.add(event.transformation);
                currentRedoQueue.clear();
            }
        }

        if (currentLayerList.size() > 0) {
            Platform.runLater(() -> {
                historyListView.scrollTo(currentLayerList.size() - 1);
            });
        }
    }

    /**
     * Event that triggers when select command occurs
     *
     * @param event
     */
    @Subscribe
    private void handleClearHistoryEvent(ClearHistoryEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        int size = items.size();
//        for (; 0 < size; size--) {
//            items.remove(items.size() - 1, items.size());
//        }
//        redoQueue.clear();
        currentLayer = 1;
        historyTitle.setText("T.History @ Layer-" + currentLayer);
        itemsArray.add(FXCollections.observableArrayList()); // index 0 (filler)
        itemsArray.add(FXCollections.observableArrayList()); // index 1
        redoQueueArray.add(new LinkedList<>()); // index 0 (filler)
        redoQueueArray.add(new LinkedList<>()); // index 1
        historyListView.setItems(itemsArray.get(currentLayer));
    }

    //@@author ihwk1996
    /**
     * Similar to TransformationEvent, but specifically for undoing/redoing all transformations
     * Called upon undo-all or redo-all
     *
     * @param event
     */
    @Subscribe
    private void handleAllTransformationEvent(AllTransformationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ObservableList<String> currentLayerList = getCurrentLayerList();
        Queue<String> currentRedoQueue = getCurrentRedoQueue();

        if (event.isRemove) {
            //undo-all command
            if (currentLayerList.size() > 0) {
                for (String item: currentLayerList) {
                    currentRedoQueue.add(item);
                }
                currentLayerList.remove(0, currentLayerList.size());
            }
        } else {
            //redo-all command
            while (currentRedoQueue.size() > 0) {
                currentLayerList.add(currentRedoQueue.poll());
            }

        }

        if (currentLayerList.size() > 0) {
            Platform.runLater(() -> {
                historyListView.scrollTo(currentLayerList.size() - 1);
            });
        }
    }

    /**
     * Event that triggers when layer is added
     *
     * @param event
     */
    @Subscribe
    private void handleLayerAddEvent(LayerAddEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        itemsArray.add(FXCollections.observableArrayList());
        redoQueueArray.add(new LinkedList<>());
    }

    /**
     * Event that triggers when layer is selected
     *
     * @param event
     */
    @Subscribe
    private void handleLayerSelectEvent(LayerSelectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        currentLayer = event.layerIndex;
        historyTitle.setText("T.History @ Layer-" + currentLayer);
        historyListView.setItems(itemsArray.get(currentLayer));
    }

    /**
     * Custom {@code ListCell} that displays transformations.
     */
    class HistoryListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String transformation, boolean empty) {
            super.updateItem(transformation, empty);

            setStyle(DEFAULT_STYLE);
            setGraphic(null);
            setText(empty ? null : transformation);

            if (!empty && getIndex() == (getListView().getItems().size() - 1)) {
                setStyle(SELECTED_STYLE);
            }
        }
    }
}

