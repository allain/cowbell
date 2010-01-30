package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;
import java.awt.geom.AffineTransform;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.examples.CameraViewTransformActivity;

public class CameraViewTransformActivityTest {

    private Camera camera;

    @Before
    public void setupCamera() {
        camera = new Camera();
    }

    @Test
    public void testStartMatrixExtractsProperly() {
        camera.setViewTransform(5, 0, 0, 5, 0, 0);
        CameraViewTransformActivity activity = new CameraViewTransformActivity(camera, new AffineTransform(), System
                        .currentTimeMillis(), 1000);
        activity.start();
        double[] startMatrix = activity.getStartMatrix();
        assertEquals(5, startMatrix[0], 0.001);
    }
}
