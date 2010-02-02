package ca.machete.cowbell.events;


public interface EventListener<EventType> {

    void dispatch(EventType object);

}
