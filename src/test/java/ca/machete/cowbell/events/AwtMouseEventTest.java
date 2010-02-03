package ca.machete.cowbell.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.Camera;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.Root;

public class AwtMouseEventTest {

    private Canvas canvas;

    @Before
    public void setupCanvas() {
        Root root = new Root();

        Camera camera = new Camera();
        root.addChild(camera);

        Layer layer = new Layer();
        root.addChild(layer);

        camera.addLayer(layer);

        this.canvas = new Canvas(camera);
    }

    @Test
    public void eventTypeExtractsFromAwtMouseEventProperly() {
        assertEventTypesCorrelate(IMouseEvent.Type.Click, MouseEvent.MOUSE_CLICKED);
        assertEventTypesCorrelate(IMouseEvent.Type.Press, MouseEvent.MOUSE_PRESSED);
        assertEventTypesCorrelate(IMouseEvent.Type.Release, MouseEvent.MOUSE_RELEASED);
        assertEventTypesCorrelate(IMouseEvent.Type.Move, MouseEvent.MOUSE_MOVED);
        assertEventTypesCorrelate(IMouseEvent.Type.Move, MouseEvent.MOUSE_DRAGGED);
    }
    
    private void assertEventTypesCorrelate(IMouseEvent.Type type, int awtType) {
        MouseEvent awtEvent = new SimpleMouseEvent(awtType, 0, 0);
        assertSame(type, new AwtMouseEvent(awtEvent).getType());
    }

    @Test
    public void canvasIsAvailableThroughMouseEvent() {
        IMouseEvent event = new AwtMouseEvent(new SimpleMouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0));
        assertSame(canvas, event.getCanvas());
    }

    @Test
    public void coveredNodesAreExtractedProperly() {
        Node node = new Node();
        node.setSize(10, 10);
        canvas.getCamera().getLayer(0).addChild(node);
        
        IMouseEvent event = new AwtMouseEvent(new SimpleMouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0));
        assertEquals(1, event.getCoveredNodes().size());
        assertSame(node, event.getCoveredNodes().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionThrownOnUnsupportedType() {
        new AwtMouseEvent(new MouseEvent(canvas, 0, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, 0, 0, 1,
                        false, MouseEvent.BUTTON1));
    }

    @Test
    public void globalPositionIsAvailableThroughEvent() {
        canvas.getCamera().setViewTransform(1, 0, 0, 1, -20, -20);

        IMouseEvent event = new AwtMouseEvent(new SimpleMouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0));

        Point2D globalPoint = event.getGlobalPoint();
        
        assertEquals(new Point2D.Double(20, 20), globalPoint);
    }

    @SuppressWarnings("serial")
    private class SimpleMouseEvent extends MouseEvent {
        public SimpleMouseEvent(int type, int x, int y) {
            super(canvas, type, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, x,y, 1, false, MouseEvent.BUTTON1);
        }
    }
}
