package ca.machete.cowbell.activities;

import static org.junit.Assert.assertEquals;
import java.awt.geom.AffineTransform;
import org.junit.Before;
import org.junit.Test;
import ca.machete.cowbell.Node;

public class NodeTransformActivityTest {

    private Node node;

    @Before
    public void setup() {
        this.node = new Node();

    }

    @Test
    public void callingStartDoesNotAffectNodeTransform() {
        NodeTransformActivity activity = new NodeTransformActivity(node, AffineTransform.getScaleInstance(2, 2), 0, 100);
        activity.start();
        assertEquals(new AffineTransform(), node.getTransform());
    }

    @Test
    public void zeroRatioIsStartTransform() {
        NodeTransformActivity activity = new NodeTransformActivity(node, AffineTransform.getScaleInstance(2, 2), 0, 100);
        activity.start();
        activity.step(0d);
        assertEquals(new AffineTransform(), node.getTransform());
    }

    @Test
    public void oneRatioIsEndTransform() {
        NodeTransformActivity activity = new NodeTransformActivity(node, AffineTransform.getScaleInstance(2, 2), 0, 100);
        activity.start();
        activity.step(1d);
        assertEquals(AffineTransform.getScaleInstance(2, 2), node.getTransform());
    }

    @Test
    public void callingStopCausesEndTransform() {
        NodeTransformActivity activity = new NodeTransformActivity(node, AffineTransform.getScaleInstance(2, 2), 0, 100);
        activity.stop();
        assertEquals(AffineTransform.getScaleInstance(2, 2), node.getTransform());
    }

}
