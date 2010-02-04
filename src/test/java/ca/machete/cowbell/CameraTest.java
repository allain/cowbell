package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import ca.machete.cowbell.activities.Activity;

public class CameraTest {

    @Test
    public void addingSameLayerMultipleTimesDoesNothing() {
        Camera camera = new Camera();
        Layer layer1 = new Layer();
        Layer layer2 = new Layer();
        camera.addLayer(layer1);
        camera.addLayer(layer2);
        camera.addLayer(layer1);

        assertSame(layer1, camera.getLayer(0));
        assertSame(layer2, camera.getLayer(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getLayerThrowsExceptionWhenIndexIsInvalid() {
        Camera camera = new Camera();
        camera.getLayer(0);
    }

    @Test
    public void getViewTransformReturnsClones() {
        Camera camera = new Camera();
        AffineTransform transform1 = camera.getViewTransform();
        AffineTransform transform2 = camera.getViewTransform();
        assertNotSame(transform1, transform2);
    }

    @Test
    public void paintingCameraPaintsAttachedLayers() {
        final List<String> calls = new ArrayList<String>();

        Camera camera = new Camera();
        camera.addLayer(new Layer() {

            @Override
            public void paint(final PaintContext paintContext) {
                super.paint(paintContext);

                calls.add("paint");
            }
        });

        camera.paint(new MockPaintContext(10, 10));
        assertEquals(1, calls.size());
    }

    @Test
    public void paintingCameraPaintsItsChildren() {
        final List<String> calls = new ArrayList<String>();

        Camera camera = new Camera();
        camera.addChild(new Node() {

            @Override
            public void paint(final PaintContext paintContext) {
                super.paint(paintContext);

                calls.add("paint child");
            }
        });

        camera.paint(new MockPaintContext(10, 10));
        assertEquals(1, calls.size());
    }

    @Test
    public void viewTransformPersistsUsingAffineTransform() {
        AffineTransform transform = AffineTransform.getScaleInstance(2, 2);

        Camera camera = new Camera();
        camera.setViewTransform(transform);
        assertEquals(2, camera.getViewTransform().getScaleX(), 0.001);
    }

    @Test
    public void viewTransformPersistsUsingDoubleArray() {
        Camera camera = new Camera();
        camera.setViewTransform(new double[] { 2, 0, 0, 2, 0, 0 });
        assertEquals(2, camera.getViewTransform().getScaleX(), 0.001);
    }

    @Test
    public void viewTransformPersistsUsingDoubleParams() {
        Camera camera = new Camera();
        camera.setViewTransform(2, 0, 0, 2, 0, 0);
        assertEquals(2, camera.getViewTransform().getScaleX(), 0.001);
    }

    @Test
    public void animateViewToTransformSchedulesActivity() {
        Root root = new Root();
        Camera camera = new Camera();
        root.addChild(camera);

        camera.animateViewToTransform(AffineTransform.getScaleInstance(2, 2), 100);

        Iterator<Activity> activityIterator = root.getScheduler().getPendingActivities().iterator();
        assertTrue(activityIterator.hasNext());
        assertNotNull(activityIterator.next());
        assertFalse(activityIterator.hasNext());
    }

    @Test
    public void viewToGlobalReturnsNullWhenTransformIsNonInvertible() {
        Root root = new Root();
        Camera camera = new Camera();
        root.addChild(camera);
        camera.setViewTransform(0, 0, 0, 0, 0, 0);
        assertNull(camera.viewToGlobal(new Point2D.Double(5, 5)));
    }
    
    @Test
    public void needsPaintingDefaultsToTrue() {
        Camera camera = new Camera();
        assertTrue(camera.needsPainting());
    }
    
    @Test
    public void needsPaintingBecomesFalseAfterPaint() {
        Camera camera = new Camera();
        camera.paint(new MockPaintContext(10, 10));
        assertFalse(camera.needsPainting());
    }
}
