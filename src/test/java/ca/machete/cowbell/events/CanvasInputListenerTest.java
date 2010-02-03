package ca.machete.cowbell.events;

import static org.junit.Assert.assertEquals;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.Camera;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Root;

public class CanvasInputListenerTest {

    private CanvasInputListener listener;

    private Canvas canvas;

    private Root root;

    private Layer layer;

    @Before
    public void buildCanvasInputListner() {
        listener = new CanvasInputListener();
        root = new Root();

        Camera camera = new Camera();
        root.addChild(camera);

        layer = new Layer();
        root.addChild(layer);

        camera.addLayer(layer);
        canvas = new Canvas(camera);
    }

    @Test
    public void mouseClickedDispatchesToHierarchy() {
        LeftClick leftClick = new LeftClick(0, 0);
        final int clickCount[] = new int[1];

        IMouseListener countingListener = new MouseAdapter() {

            @Override
            public void mouseClicked(IMouseEvent mouseEvent) {
                clickCount[0]++;
            }
        };

        root.addMouseListener(countingListener);
        layer.addMouseListener(countingListener);
        layer.setSize(100, 100);

        listener.mouseClicked(leftClick);
        
        assertEquals(2, clickCount[0]);
    }
    
    @Test
    public void mousePressedDispatchesToHierarchy() {
        final int pressCount[] = new int[1];

        IMouseListener countingListener = new MouseAdapter() {

            @Override
            public void mousePressed(IMouseEvent mouseEvent) {
                pressCount[0]++;
            }
        };

        root.addMouseListener(countingListener);
        layer.addMouseListener(countingListener);
        layer.setSize(100, 100);

        listener.mousePressed(new LeftPress(0, 0));
        
        assertEquals(2, pressCount[0]);
    }

    @Test
    public void mouseReleasedDispatchesToHierarchy() {
        LeftRelease leftRelease = new LeftRelease(0, 0);
        final int releaseCount[] = new int[1];

        IMouseListener countingListener = new MouseAdapter() {

            @Override
            public void mouseReleased(IMouseEvent mouseEvent) {
                releaseCount[0]++;
            }
        };

        root.addMouseListener(countingListener);
        layer.addMouseListener(countingListener);
        layer.setSize(100, 100);

        listener.mouseReleased(leftRelease);
        
        assertEquals(2, releaseCount[0]);
    }

    
    @SuppressWarnings("serial")
    private class LeftClick extends MouseEvent {

        LeftClick(final int x, final int y) {
            super(canvas, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, x, y, 1,
                            false, MouseEvent.BUTTON1);
        }
    }
    
    @SuppressWarnings("serial")
    private class LeftPress extends MouseEvent {

        LeftPress(final int x, final int y) {
            super(canvas, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, x, y, 1,
                            false, MouseEvent.BUTTON1);
        }
    }
    
    @SuppressWarnings("serial")
    private class LeftRelease extends MouseEvent {

        LeftRelease(final int x, final int y) {
            super(canvas, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, x, y, 1,
                            false, MouseEvent.BUTTON1);
        }
    }
}
