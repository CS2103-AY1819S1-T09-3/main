package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ihwk1996

/**
 * An event that notifies HistoryListPanel on a change of Layer
 */
public class LayerSelectEvent extends BaseEvent {

    public final int layerIndex;

    public LayerSelectEvent(int layerIndex) {
        this. layerIndex = layerIndex;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
