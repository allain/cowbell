package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CanvasTest {

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
    public void canvasShouldConstructWithoutChildrenByDefault() {
        assertEquals(0, canvas.getComponentCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canvasConstructorDoesNotAcceptNullCamera() {
        new Canvas(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canvasConstructorDoesNotAcceptUnattachedCameras() {
        Camera camera = new Camera();
        new Canvas(camera);
    }

    @Test
    public void canvasCameraIsFinal() {
        Root root = new Root();
        Camera camera = new Camera();
        root.addChild(camera);
        Canvas canvas = new Canvas(camera);
        assertSame(camera, canvas.getCamera());
    }

    @Test
    public void paintingCanvasSetClipOnGraphicsToItsSize() {
        canvas.setSize(100, 100);
        Graphics2D graphics = buildGraphics2D();
        canvas.paint(graphics);
        assertEquals(new Rectangle(100, 100), graphics.getClip());
    }

    @Test
    public void paintingCanvasDefersPaintingToItsCamera() {
        final List<String> calls = new ArrayList<String>();

        Root root = new Root();
        Camera camera = new Camera() {

            @Override
            public void paint(final PaintContext paintContext) {
                calls.add("Camera.paint");
            }
        };
        root.addChild(camera);
        Canvas canvas = new Canvas(camera);
        canvas.paint(buildGraphics2D());

        assertEquals(1, calls.size());
    }

    @SuppressWarnings("serial")
    @Test
    public void canvasInstallsRepaintActivityOnRoot() {
        final List<String> calls = new ArrayList<String>();

        Root root = new Root();
        Camera camera = new Camera();
        root.addChild(camera);
        Canvas canvas = new Canvas(camera) {

            @Override
            public void repaint() {
                calls.add("Canvas.paint");
            }
        };
        canvas.setSize(new Dimension(100, 100));

        assertEquals(1, camera.getRoot().getScheduler().getPendingActivities().size());
        camera.getRoot().getScheduler().tick();

        assertEquals(1, calls.size());
    }

    private Graphics2D buildGraphics2D() {
        return (Graphics2D) new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB).getGraphics();
    }

    @Test
    public void nodesAtReturnsLayerEvenWhenSceneIsEmpty() {
        Layer layer = canvas.getCamera().getLayer(0);
        layer.setSize(100, 100);

        List<Node> coveredNodes = canvas.getNodesAt(0, 0);
        assertEquals(1, coveredNodes.size());
        assertEquals(layer, coveredNodes.get(0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void nodesAtThrowsExceptionWhenViewTransformIsNonInvertible() {
        Layer layer = canvas.getCamera().getLayer(0);
        layer.setSize(100, 100);

        canvas.getCamera().setViewTransform(0, 0, 0, 0, 0, 0);
        List<Node> coveredNodes = canvas.getNodesAt(0, 0);
        assertNull(coveredNodes);
    }

    @Test
    public void nodesAtReturnsMultipleOverlaps() {
        Layer layer = canvas.getCamera().getLayer(0);

        Node node1 = new Node();
        node1.setSize(10, 10);

        Node node2 = new Node();
        node2.setSize(100, 5);

        layer.addChild(node1);
        layer.addChild(node2);

        List<Node> nodesAt = canvas.getNodesAt(3, 3);

        assertEquals(2, nodesAt.size());
        assertSame(node1, nodesAt.get(0));
        assertSame(node2, nodesAt.get(1));
    }

    @Test
    public void nodesAtReturnsTheDeepestNodePossible() {
        Layer layer = canvas.getCamera().getLayer(0);
        layer.setSize(100, 100);

        Node node1 = new Node();
        layer.addChild(node1);
        node1.setSize(10, 10);

        Node node2 = new Node();
        node1.addChild(node2);
        node2.setSize(10, 10);

        Node node3 = new Node();
        node2.addChild(node3);

        node3.setSize(10, 10);

        List<Node> nodesAt = canvas.getNodesAt(5, 5);
        assertEquals(1, nodesAt.size());
        assertSame(node3, nodesAt.get(0));
    }

    @Test
    public void stickyNodesTrumpNodesOnLayer() {
        Camera camera = canvas.getCamera();
        Layer layer = camera.getLayer(0);
        layer.setSize(100, 100);

        Node stickyNode = new Node();
        stickyNode.setSize(10, 10);
        camera.addChild(stickyNode);

        List<Node> nodesAt = canvas.getNodesAt(5, 5);
        assertEquals(1, nodesAt.size());
        assertSame(stickyNode, nodesAt.get(0));

    }
}
