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
        MouseEvent leftClick = new SimpleMouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0);

        CountingListener countingListener = buildSceneAndReturnCountingListener();

        listener.mouseClicked(leftClick);

        assertEquals(2, countingListener.getClickCount());
    }

    @Test
    public void mousePressedDispatchesToHierarchy() {
        CountingListener countingListener = buildSceneAndReturnCountingListener();

        listener.mousePressed(new SimpleMouseEvent(MouseEvent.MOUSE_PRESSED, 0, 0));

        assertEquals(2, countingListener.getPressCount());
    }

    @Test
    public void mouseReleasedDispatchesToHierarchy() {
        CountingListener countingListener = buildSceneAndReturnCountingListener();

        listener.mouseReleased(new SimpleMouseEvent(MouseEvent.MOUSE_RELEASED, 0, 0));

        assertEquals(2, countingListener.getReleaseCount());
    }

    @Test
    public void mouseMovedDispatchesToHierarchy() {
        CountingListener countingListener = buildSceneAndReturnCountingListener();

        listener.mouseMoved(new SimpleMouseEvent(MouseEvent.MOUSE_MOVED, 0, 0));

        assertEquals(2, countingListener.getMoveCount());
    }

    @Test
    public void mouseDraggedDispatchesToHierarchy() {
        CountingListener countingListener = buildSceneAndReturnCountingListener();

        listener.mouseDragged(new SimpleMouseEvent(MouseEvent.MOUSE_DRAGGED, 0, 0));

        assertEquals(2, countingListener.getMoveCount());
    }

    private CountingListener buildSceneAndReturnCountingListener() {
        CountingListener countingListener = new CountingListener();

        root.addMouseListener(countingListener);
        layer.addMouseListener(countingListener);
        layer.setSize(100, 100);
        return countingListener;
    }
    
    @Test
    public void mouseEnteredDoesNothing() {
        listener.mouseEntered(null);
    }

    @Test
    public void mouseExitedDoesNothing() {
        listener.mouseExited(null);
    }

    @SuppressWarnings("serial")
    private class SimpleMouseEvent extends MouseEvent {

        public SimpleMouseEvent(int type, int x, int y) {
            super(canvas, type, System.currentTimeMillis(), InputEvent.BUTTON1_MASK, x, y, 1, false, MouseEvent.BUTTON1);
        }
    }

    private static class CountingListener implements IMouseListener {

        private int clickCount;

        private int pressCount;

        private int releaseCount;

        private int moveCount;

        @Override
        public void mouseClicked(IMouseEvent mouseEvent) {
            clickCount++;
        }

        @Override
        public void mouseMoved(IMouseEvent mouseEvent) {
            moveCount++;
        }

        @Override
        public void mousePressed(IMouseEvent mouseEvent) {
            pressCount++;
        }

        @Override
        public void mouseReleased(IMouseEvent mouseEvent) {
            releaseCount++;
        }

        public int getClickCount() {
            return clickCount;
        }

        public int getPressCount() {
            return pressCount;
        }

        public int getReleaseCount() {
            return releaseCount;
        }

        public int getMoveCount() {
            return moveCount;
        }

    }
}
