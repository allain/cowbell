package ca.machete.cowbell.activities;

import java.awt.geom.AffineTransform;
import ca.machete.cowbell.Camera;

public class CameraViewTransformActivity extends TimedActivity {

    private final Camera camera;

    private final double[] startMatrix = new double[6];

    private final double[] targetMatrix = new double[6];

    public CameraViewTransformActivity(final Camera camera, final AffineTransform target, final long startTime,
                    final long duration) {
        super(startTime, duration);

        this.camera = camera;

        target.getMatrix(targetMatrix);
    }

    @Override
    public void start() {
        camera.getViewTransform().getMatrix(startMatrix);
    }

    @Override
    public void step(final double ratioCompleted) {
        double[] currentMatrix = new double[6];

        for (int i = 0; i < 6; i++)
            currentMatrix[i] = (targetMatrix[i] - startMatrix[i]) * ratioCompleted + startMatrix[i];

        camera.setViewTransform(currentMatrix);
    }

    public double[] getStartMatrix() {
        return startMatrix;
    }

}
