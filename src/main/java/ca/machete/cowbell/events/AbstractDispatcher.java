package ca.machete.cowbell.events;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public abstract class AbstractDispatcher<EventType> {
    private final List<EventType> listeners = new LinkedList<EventType>();
    
    public final void addListener(EventType listener) {
        listeners.add(listener);
    }
    
    public final void removeListener(EventType listener) {
        while (listeners.remove(listener)) {
            // listener removed
        }
    }
    
    public final boolean hasListeners() {
        return !listeners.isEmpty();
    }

    protected final Iterable<EventType> getListeners() {
        return new ArrayList<EventType>(listeners);
    }
}
