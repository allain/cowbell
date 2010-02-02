package ca.machete.cowbell.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.Camera;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.Root;

public class AwtMouseEventTest {

    private Canvas canvas;
    private List<Node> emptyNodes;

    @Before
    public void setupCanvas() {
        Root root = new Root();
        
        Camera camera = new Camera();
        root.addChild(camera);
        
        Layer layer = new Layer();
        root.addChild(layer);
        
        camera.addLayer(layer);
        
        this.canvas = new Canvas(camera);
        
        this.emptyNodes = new ArrayList<Node>();
    }

    @Test
    public void eventTypeExtractsFromAwtMouseEventProperly() {
        IMouseEvent event = new AwtMouseEvent(new LeftClick(0, 0), emptyNodes);
        assertSame(IMouseEvent.Type.Clicked, event.getType());
    }
    
    @Test
    public void canvasIsAvailableThroughMouseEvent() {
        LeftClick leftClick = new LeftClick(0, 0);
        IMouseEvent event = new AwtMouseEvent(leftClick, emptyNodes);
        assertSame(canvas, event.getCanvas());
    }
    
    @Test
    public void coveredNodesArePassedThrough() {
        LeftClick leftClick = new LeftClick(0, 0);
        List<Node> coveredNodes = new ArrayList<Node>();
        coveredNodes.add(new Node());
        IMouseEvent event = new AwtMouseEvent(leftClick, coveredNodes);
        assertSame(coveredNodes, event.getCoveredNodes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionThrownOnUnsupportedType() {
        new AwtMouseEvent(new MouseEvent(canvas, 0, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, 0, 0, 1,
                        false, MouseEvent.BUTTON1), emptyNodes);
    }
    
    @Test
    public void globalPositionIsAvailableThroughEvent() {
        LeftClick leftClick = new LeftClick(0, 0);
        canvas.getCamera().setViewTransform(1, 0, 0, 1, -20, -20);
        
        IMouseEvent event = new AwtMouseEvent(leftClick, emptyNodes);
      
        Point2D globalPoint = event.getGlobalPoint();
        assertEquals(new Point2D.Double(20, 20), globalPoint);
    }

    @SuppressWarnings("serial")
    private class LeftClick extends MouseEvent {
        LeftClick(int x, int y) {
            super(canvas, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, x, y, 1,
                            false, MouseEvent.BUTTON1);
        }
    }

}
