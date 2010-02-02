package ca.machete.cowbell.events.filters;

import ca.machete.cowbell.events.EventFilter;
import ca.machete.cowbell.events.IMouseEvent;
import ca.machete.cowbell.events.IMouseEvent.Type;

public class MouseEventTypeFilter implements EventFilter<IMouseEvent> {

    private final IMouseEvent.Type type;

    public MouseEventTypeFilter(final Type type) {
        this.type = type;
    }

    @Override
    public boolean test(final IMouseEvent mouseEvent) {
        return mouseEvent.getType() == type;
    }

}
