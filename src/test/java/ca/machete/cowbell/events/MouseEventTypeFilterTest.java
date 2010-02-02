package ca.machete.cowbell.events;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.events.filters.MockIMouseEvent;
import ca.machete.cowbell.events.filters.MouseEventTypeFilter;

public class MouseEventTypeFilterTest {

    @Test
    public void testPassesWhenTypeCorrect() {
        List<Node> nodes = new ArrayList<Node>();
        IMouseEvent mouseEvent = new MockIMouseEvent(IMouseEvent.Type.Clicked, nodes, 0, 0);

        EventFilter filter = new MouseEventTypeFilter(IMouseEvent.Type.Clicked);
        assertTrue(filter.test(mouseEvent));
    }
}
