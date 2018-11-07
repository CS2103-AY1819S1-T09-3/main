package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ihwk1996

/**
 * An event that notifies HistoryListPanel on a change of Layer
 */
public class LayerAddEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
