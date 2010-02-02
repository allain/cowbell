package ca.machete.cowbell.events;

public interface EventFilter<EventType> {

    boolean test(EventType object);
}
