package seedu.address.ui;

//@@author chivent

import java.util.PriorityQueue;
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
import seedu.address.commons.events.ui.ClearHistoryEvent;
import seedu.address.commons.events.ui.TransformationEvent;

/**
 * Panel containing the list of past transformations.
 */
public class HistoryListPanel extends UiPart<Region> {
    private static final String FXML = "HistoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private ObservableList<String> items = FXCollections.observableArrayList();

    /**
     * Stores transformations that have been undone.
     */
    private PriorityQueue<String> redoQueue = new PriorityQueue<>();

    @FXML
    private ListView<String> historyListView;

    @FXML
    private Text historyTitle;

    public HistoryListPanel() {
        super(FXML);
        historyTitle.setText("Transformation History");
        historyListView.setItems(items);
        historyListView.setCellFactory(listView -> new HistoryListPanel.HistoryListViewCell());
        registerAsAnEventHandler(this);
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

        if (event.isRemove) {
            //undo command
            if (items.size() > 0) {
                redoQueue.add(items.get(items.size() - 1));
                items.remove(items.size() - 1, items.size());
            }
        } else {
            if (event.transformation.isEmpty()) {
                //redo command
                if (redoQueue.size() > 0) {
                    items.add(redoQueue.poll());
                }
            } else {
                //add command
                items.add(event.transformation);
                redoQueue.clear();
            }
        }

        if (items.size() > 0) {
            Platform.runLater(() -> {
                historyListView.scrollTo(items.size() - 1);
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
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        int size = items.size();
        for (; 0 < size; size--) {
            items.remove(items.size() - 1, items.size());
        }
        redoQueue.clear();
    }

    /**
     * Custom {@code ListCell} that displays transformations.
     */
    class HistoryListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String transformation, boolean empty) {
            super.updateItem(transformation, empty);

            setStyle("-fx-background-color: transparent;  -fx-text-fill: #6e6e6e;");
            setGraphic(null);
            setText(empty ? null : transformation);

            if (!empty && getIndex() == (getListView().getItems().size() - 1)) {
                setStyle("-fx-background-color: #a3a3a3;  -fx-text-fill: #1f1f1f;");
            }
        }
    }
}

