package ca.machete.cowbell;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import java.util.Iterator;
import org.junit.Test;

public class RootTest {

    @Test
    public void getRootReturnsSelf() {
        Root root = new Root();
        assertSame(root, root.getRoot());
    }

    @Test
    public void getCamerasReturnsEmptyWhenNoChildrenAreCameras() {
        Root root = new Root();
        Iterable<Camera> cameras = root.getCameras();
        assertFalse(cameras.iterator().hasNext());
    }

    @Test
    public void getLayersReturnsEmptyWhenNoChildrenAreLayers() {
        Root root = new Root();
        Iterable<Layer> layers = root.getLayers();
        assertFalse(layers.iterator().hasNext());
    }

    @Test
    public void getLayersReturnsLayersIfTheyExist() {
        Root root = buildTestRoot();

        Iterator<Layer> layers = root.getLayers().iterator();
        for (int i = 0; i < 3; i++) {
            assertNotNull(layers.next());
        }

        assertFalse(layers.hasNext());
    }

    @Test
    public void getCamerasReturnsCamerasIfTheyExist() {
        Root root = buildTestRoot();

        Iterator<Camera> cameras = root.getCameras().iterator();
        for (int i = 0; i < 2; i++) {
            assertNotNull(cameras.next());
        }

        assertFalse(cameras.hasNext());
    }

    @Test
    public void schedulerIsValidByDefault() {
        Root root = new Root();
        assertNotNull(root.getScheduler());
    }

    private Root buildTestRoot() {
        Root root = new Root();

        root.addChild(new Camera());
        root.addChild(new Layer());
        root.addChild(new Camera());
        root.addChild(new Layer());
        root.addChild(new Layer());

        return root;
    }
}
