package ca.machete.cowbell.events.filters;

import ca.machete.cowbell.Node;
import ca.machete.cowbell.events.EventFilter;
import ca.machete.cowbell.events.IMouseEvent;

public class MouseClassEventFilter implements EventFilter<IMouseEvent> {

    public final Class<?> targetClass;

    public MouseClassEventFilter(final Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public boolean test(final IMouseEvent event) {
        for (Node node : event.getCoveredNodes()) {
            Node currentNode = node;
            do {
                if (targetClass.isAssignableFrom(currentNode.getClass()))
                    return true;

                currentNode = currentNode.getParent();
            } while (currentNode != null);
        }

        return false;
    }

    public Class<?> getFilteredClass() {
        return targetClass;
    }

}
