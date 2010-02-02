package ca.machete.cowbell.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventDispatcher<EventType> {

    private final Map<EventFilter<EventType>, Set<EventListener<EventType>>> registrations;

    public EventDispatcher() {
        registrations = new HashMap<EventFilter<EventType>, Set<EventListener<EventType>>>();
    }

    public void register(EventFilter<EventType> filter, EventListener<EventType> listener) {
        if (filter == null)
            throw new IllegalArgumentException("filter may not be null");

        if (listener == null)
            throw new IllegalArgumentException("filter may not be null");

        if (!registrations.containsKey(filter))
            registrations.put(filter, new HashSet<EventListener<EventType>>());

        registrations.get(filter).add(listener);
    }

    public void dispatch(EventType event) {
        List<Exception> exceptions = new LinkedList<Exception>();
        
        for (Map.Entry<EventFilter<EventType>, Set<EventListener<EventType>>> entry : registrations.entrySet()) {
            EventFilter<EventType> filter = entry.getKey();
            if (filter.test(event)) {
                for (EventListener<EventType> listener : entry.getValue()) {
                    try {
                        listener.dispatch(event);
                    } catch (Exception e) {
                        exceptions.add(e);
                    }
                }
            }
        }
        
        if (!exceptions.isEmpty())
            throw new DispatcherException(exceptions);
    }
}
