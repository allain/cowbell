package ca.machete.cowbell.activities;

import java.awt.geom.AffineTransform;
import ca.machete.cowbell.Node;

public class NodeTransformActivity extends TimedActivity {

    private final Node node;

    private final double[] startMatrix = new double[6];

    private final double[] targetMatrix = new double[6];

    public NodeTransformActivity(final Node node, final AffineTransform target, final long startTime,
                    final long duration) {
        super(startTime, duration);

        this.node = node;
        target.getMatrix(targetMatrix);
    }

    @Override
    public void start() {
        node.getTransform().getMatrix(startMatrix);
    }

    @Override
    public void step(final double ratioCompleted) {
        double[] currentMatrix = new double[6];
        for (int i = 0; i < 6; i++) {
            currentMatrix[i] = (targetMatrix[i] - startMatrix[i]) * ratioCompleted + startMatrix[i];
        }

        node.setTransform(currentMatrix);
    }

}
