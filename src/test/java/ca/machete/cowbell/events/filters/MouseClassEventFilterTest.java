package ca.machete.cowbell.events.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.events.IMouseEvent;

public class MouseClassEventFilterTest {

    @Test
    public void constructorPersistsClass() {
        MouseClassEventFilter filter = new MouseClassEventFilter(Node.class);

        assertSame(Node.class, filter.getFilteredClass());
    }

    @Test
    public void testFailsWhenNoMatchingClassesInCoveredNode() {
        MouseClassEventFilter filter = new MouseClassEventFilter(TestNode.class);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node());
        nodes.add(new Node());

        IMouseEvent mouseEvent = new MockIMouseEvent(IMouseEvent.Type.Clicked, nodes, 0, 0);
        assertFalse(filter.test(mouseEvent));
    }

    @Test
    public void testSucceedsWhenMatchingNodeIsCoveredNode() {
        MouseClassEventFilter filter = new MouseClassEventFilter(TestNode.class);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node());
        nodes.add(new TestNode());

        IMouseEvent mouseEvent = new MockIMouseEvent(IMouseEvent.Type.Clicked, nodes, 0, 0);
        assertTrue(filter.test(mouseEvent));
    }

    @Test
    public void testSucceedsWhenMatchingNodeIsParentOfCoveredNode() {
        MouseClassEventFilter filter = new MouseClassEventFilter(TestNode.class);
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(new Node());

        TestNode parent = new TestNode();
        Node child = new Node();
        parent.addChild(child);
        nodes.add(child);

        IMouseEvent mouseEvent = new MockIMouseEvent(IMouseEvent.Type.Clicked, nodes, 0, 0);
        assertTrue(filter.test(mouseEvent));
    }

    class TestNode extends Node {

    }

}
