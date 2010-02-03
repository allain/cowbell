package ca.machete.cowbell.activities;

import static org.junit.Assert.assertEquals;
import java.awt.geom.AffineTransform;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.Camera;

public class CameraViewTransformActivityTest {

    private Camera camera;

    @Before
    public void setupCamera() {
        camera = new Camera();
    }

    @Test
    public void startMatrixExtractsProperly() {
        camera.setViewTransform(5, 0, 0, 5, 0, 0);
        CameraViewTransformActivity activity = new CameraViewTransformActivity(camera, new AffineTransform(), System
                        .currentTimeMillis(), 1000);
        activity.start();
        double[] startMatrix = activity.getStartMatrix();
        assertEquals(5, startMatrix[0], 0.001);
    }

    @Test
    public void zeroRatioEquatesToStart() {
        CameraViewTransformActivity activity = new CameraViewTransformActivity(camera, AffineTransform
                        .getScaleInstance(5, 5), System.currentTimeMillis(), 1000);
        activity.start();
        activity.step(0d);
        assertEquals(1d, camera.getViewTransform().getScaleX(), 0.001);
    }

    @Test
    public void oneRatioEquatesToTarget() {
        CameraViewTransformActivity activity = new CameraViewTransformActivity(camera, AffineTransform
                        .getScaleInstance(5, 5), System.currentTimeMillis(), 1000);
        activity.start();
        activity.step(1d);
        assertEquals(5d, camera.getViewTransform().getScaleX(), 0.001);
    }
}
