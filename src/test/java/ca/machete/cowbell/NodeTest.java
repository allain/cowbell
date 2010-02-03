package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.events.IMouseEvent;
import ca.machete.cowbell.events.MockIMouseEvent;
import ca.machete.cowbell.events.MouseAdapter;
import ca.machete.cowbell.layouts.Layout;
import ca.machete.cowbell.painters.Painter;

public class NodeTest {

    private Painter mockPainter;
    private MockMouseListener mockListener;

    @Before
    public void buildMockPainter() {
        mockPainter = new Painter() {
            public void paint(final Node node, final PaintContext paintContext) {
            }
        };
    }
    
    @Before
    public void buildMockMouseListener() {
        mockListener = new MockMouseListener();
    }

    @Test
    public void providingNullAsLayoutToConstructorYieldsNonNullLayout() {
        Node node = new Node(null);
        assertNotNull(node.getLayout());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addChildDoesNotAcceptNull() {
        Node node = new Node();
        node.addChild(null);
    }

    @Test
    public void addChildAgainMovesItToEnd() {
        Node parent = new Node();
        Node child = new Node();
        parent.addChild(child);

        parent.addChild(new Node());

        parent.addChild(child);

        Iterator<Node> iterator = parent.getChildren().iterator();
        iterator.next();
        assertSame(child, iterator.next());
    }

    @Test
    public void addChildOfExistingNodeTransfersIt() {
        Node parent1 = new Node();
        Node parent2 = new Node();
        Node child = new Node();
        parent1.addChild(child);
        parent2.addChild(child);
        assertFalse(parent1.getChildren().iterator().hasNext());

        assertSame(child, parent2.getChildren().iterator().next());
    }

    @Test
    public void addPainterAllowsPainterMultipleTimes() {
        Node node = new Node();

        node.addPainter(mockPainter);
        node.addPainter(mockPainter);
        node.addPainter(mockPainter);

        int painterCount = 0;
        for (Painter p : node.getPainters()) {
            assertSame(mockPainter, p);
            painterCount++;
        }

        assertEquals(3, painterCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPainterDoesNotAcceptNull() {
        Node node = new Node();
        node.addPainter(null);
    }

    @Test
    public void childrenShouldNotBeNullEvenWhenNoChildren() {
        Node node = new Node();
        assertNotNull(node.getChildren());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void childrenShouldBeImmutable() {
        Node node = new Node();
        node.addChild(new Node());

        Iterable<Node> children = node.getChildren();
        Iterator<Node> iterator = children.iterator();
        iterator.next();
        iterator.remove();
    }

    @Test
    public void sizePersists() {
        Node node = new Node();
        node.setSize(100d, 50d);
        assertEquals(100d, node.getWidth(), 0.001);
        assertEquals(50d, node.getHeight(), 0.001);
    }

    @Test
    public void modelPersists() {
        Node node = new Node();

        Object model = new Object();
        node.setModel(model);
        assertSame(model, node.getModel());
    }

    @Test
    public void modelAllowsNull() {
        Node node = new Node();
        node.setModel(new Object());
        node.setModel(null);
        assertNull(node.getModel());
    }

    @Test
    public void rootShouldBeNullOnUnattachedNode() {
        Node node = new Node();
        assertNull(node.getRoot());
    }

    @Test
    public void rootShouldBeNullWhenNodeDoesNotHaveRootInItsHierarchy() {
        Node node = new Node();
        Node parent = new Node();
        parent.addChild(node);

        assertNull(node.getRoot());
    }

    @Test
    public void rootShouldNotBeNullWhenNodeInValidScene() {
        Node node = new Node();
        Node parent = new Node();
        Root root = new Root();
        root.addChild(parent);
        parent.addChild(node);

        assertSame(root, node.getRoot());
    }

    @Test
    public void transformIsCloned() {
        Node node = new Node();
        AffineTransform transform1 = node.getTransform();
        node.setTransform(2, 0, 0, 2, 0, 0);
        assertFalse(transform1.equals(node.getTransform()));
    }

    @Test
    public void transformCanBeSetWithMatrix() {
        Node node = new Node();
        double[] matrix = new double[] { 2, 0, 0, 2, 5, 10 };
        node.setTransform(matrix);

        double[] actualMatrix = new double[6];
        node.getTransform().getMatrix(actualMatrix);

        for (int i = 0; i < 6; i++) {
            assertEquals(matrix[i], actualMatrix[i], 0.001);
        }
    }

    @Test
    public void transformCanBeSetWithTransform() {
        AffineTransform transform = AffineTransform.getTranslateInstance(1, 2);
        Node node = new Node();
        node.setTransform(transform);
        assertEquals(transform, node.getTransform());
    }

    @Test
    public void indexOfPainterThatIsntInPainterIsNegativeOne() {
        Node node = new Node();
        assertEquals(-1, node.indexOfPainter(mockPainter));
    }

    @Test
    public void indexOfPainterReturnsNegativeOneOnNull() {
        Node node = new Node();
        assertEquals(-1, node.indexOfPainter(null));
    }

    @Test
    public void invalidateLayoutBubblesUpToParent() {
        Node node = new Node();

        final List<String> calls = new ArrayList<String>();
        Node parent = new Node() {

            @Override
            public void invalidateLayout() {
                super.invalidateLayout();
                calls.add("invalidLayout");
            }
        };

        parent.addChild(node);
        parent.layout();
        node.invalidateLayout();

        assertEquals(1, calls.size());
    }

    @Test
    public void paintDispatchesToPainters() {
        final List<String> calls = new ArrayList<String>();

        Node node = new Node();
        node.setSize(10, 10);
        node.addPainter(new Painter() {

            public void paint(final Node node, final PaintContext paintContext) {
                calls.add("painted");
            }
        });

        PaintContext paintContext = buildPaintContext(10, 10);
        node.paint(paintContext);

        assertEquals(1, calls.size());
    }

    @Test
    public void paintDoesNotPaintWhenOutsideClippingRegion() {
        final List<String> calls = new ArrayList<String>();

        Node node = new Node();
        node.setTransform(1, 0, 0, 1, 12, 0);
        node.setSize(10, 10);
        node.addPainter(new Painter() {

            public void paint(final Node node, final PaintContext paintContext) {
                calls.add("painted");
            }
        });

        PaintContext paintContext = buildPaintContext(10, 10);
        node.paint(paintContext);

        assertEquals(0, calls.size());
    }

    private PaintContext buildPaintContext(final int width, final int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setClip(new Rectangle(width, height));
        PaintContext paintContext = new PaintContext(graphics);
        return paintContext;
    }

    @Test
    public void fullBoundsShouldBeSizeWhenNoChildren() {
        Node node = new Node();
        node.setSize(10, 20);
        Rectangle2D bounds = node.getFullBounds();
        assertEquals(0, bounds.getMinX(), 0.0001);
        assertEquals(0, bounds.getMinY(), 0.0001);
        assertEquals(10, bounds.getMaxX(), 0.0001);
        assertEquals(20, bounds.getMaxY(), 0.0001);
    }

    @Test
    public void fullBoundsIncludesChildEvenWhenNoSizeItself() {
        Node node = new Node();
        Node child = new Node();
        child.setSize(10, 20);
        node.addChild(child);

        Rectangle2D bounds = node.getFullBounds();
        assertEquals(0, bounds.getMinX(), 0.0001);
        assertEquals(0, bounds.getMinY(), 0.0001);
        assertEquals(10, bounds.getMaxX(), 0.0001);
        assertEquals(20, bounds.getMaxY(), 0.0001);
    }

    @Test
    public void fullBoundsIncludesChildWhenChildTranslated() {
        Node node = new Node();
        Node child = new Node();
        child.setTransform(1, 0, 0, 1, 10, 10);
        child.setSize(10, 20);
        node.addChild(child);

        Rectangle2D bounds = node.getFullBounds();
        System.out.println(bounds);
        assertEquals(0, bounds.getMinX(), 0.0001);
        assertEquals(0, bounds.getMinY(), 0.0001);
        assertEquals(20, bounds.getMaxX(), 0.0001);
        assertEquals(30, bounds.getMaxY(), 0.0001);
    }

    @Test
    public void fullBoundsIsBoundsWhenTransformIsIdentity() {
        Node node = new Node();
        node.setSize(10, 20);
        Rectangle2D fullBounds = node.getFullBounds();
        assertEquals(0, fullBounds.getMinX(), 0.001);
        assertEquals(0, fullBounds.getMinY(), 0.001);
        assertEquals(10, fullBounds.getWidth(), 0.001);
        assertEquals(20, fullBounds.getHeight(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void layoutMayNotBeNull() {
        Node node = new Node();
        node.setLayout(null);
    }

    @Test
    public void layoutPersists() {
        Node node = new Node();
        Layout layout = new Layout() {

            @Override
            public void layout(final List<Node> nodes, final double width, final double height) {
            }
        };

        node.setLayout(layout);
        assertSame(layout, node.getLayout());
    }

    @Test
    public void childrenGetPaintedWhenNodeIsPainted() {
        final List<String> calls = new ArrayList<String>();

        Node parent = new Node();
        Node child = new Node() {

            @Override
            public void paint(final PaintContext context) {
                super.paint(context);

                calls.add("paint");
            }
        };
        parent.setSize(10, 10);
        child.setSize(10, 10);
        parent.addChild(child);

        PaintContext paintContext = buildPaintContext(10, 10);
        parent.paint(paintContext);
        assertEquals(1, calls.size());
    }

    @Test
    public void localToParentReturnsExpected() {
        Node node = new Node();
        node.setTransform(2, 0, 0, 2, 0, 0);
        Point2D parentPoint = node.localToParent(new Point2D.Double(1, 1));
        assertEquals(new Point2D.Double(0.5, 0.5), parentPoint);
    }

    @Test
    public void localToParentReturnsNullWhenMatrixIsNotInvertible() {
        Node node = new Node();
        node.setTransform(0, 0, 0, 0, 0, 0);
        Point2D parentPoint = node.localToParent(new Point2D.Double(1, 1));
        assertNull(parentPoint);
    }

    @Test
    public void parentToLocalReturnsExpected() {
        Node node = new Node();
        node.setTransform(2, 0, 0, 2, 0, 0);
        Point2D localPoint = node.parentToLocal(new Point2D.Double(1, 1));
        assertEquals(new Point2D.Double(2, 2), localPoint);
    }

    @Test
    public void parentPersists() {
        Node child = new Node();
        Node parent = new Node();

        parent.addChild(child);
        assertSame(parent, child.getParent());

        parent.removeChild(child);
        assertNull(child.getParent());
    }

    @Test
    public void registratinOfMouseListenerDoesSo() {
        Node node = new Node();
        
        node.addMouseListener(mockListener);
        
        IMouseEvent mouseEvent = new MockIMouseEvent(IMouseEvent.Type.Click, Collections.<Node>emptyList(), 0, 0);
        node.getMouseListenerDispatcher().mouseClicked(mouseEvent);
        assertEquals(1, mockListener.getClickCount());
    }
    
    @Test
    public void removeMouseListenerDoesNothingWhenListenerIsMissing() {
        Node node = new Node();
        MouseAdapter missingAdapter = new MouseAdapter();
        node.addMouseListener(new MouseAdapter());
        node.removeMouseListener(missingAdapter);
    }
    
    @Test
    public void removingListenerWorks() {
        Node node = new Node();
        MouseAdapter listener = new MouseAdapter();
        node.addMouseListener(listener);
        node.removeMouseListener(listener);
        node.getMouseListenerDispatcher().mouseClicked(null);
    }
    
    private static class MockMouseListener extends MouseAdapter {
        private int clickCount;

        @Override
        public void mouseClicked(IMouseEvent mouseEvent) {
            clickCount ++;
        }
        
        public int getClickCount() {
            return clickCount;
        }
    }
}
