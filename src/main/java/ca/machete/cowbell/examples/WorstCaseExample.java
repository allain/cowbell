package ca.machete.cowbell.examples;

import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.painters.BorderPainter;

public class WorstCaseExample extends AbstractExample {

    private static final int NODE_COUNT = 10000;

    static final BorderPainter borderPainter = new BorderPainter();

    public static void main(final String[] args) {
        WorstCaseExample example = new WorstCaseExample();

        example.run();
    }

    @Override
    public void run() {
        Canvas canvas = buildSimpleCanvas();

        Layer layer = canvas.getCamera().getLayer(0);

        addNodesToLayer(layer, NODE_COUNT);

        layer.setLayout(new JitterLayout(2));

        layer.getRoot().getScheduler().schedule(new InvalidateLayoutActivity(layer));

        JFrame frame = wrapCanvasWithFrame(canvas, NODE_COUNT + " jitterning nodes with 1 common parent");

        frame.setVisible(true);
    }

    private static class JitterLayout extends RandomLayout {

        private final double[] MATRIX = new double[6];

        private boolean first = true;

        private final Random random = new Random();

        private final double jitterSize;

        public JitterLayout(final double jitterSize) {
            this.jitterSize = jitterSize;
        }

        @Override
        public void layout(final List<Node> nodes, final double width, final double height) {
            if (first) {
                super.layout(nodes, width, height);
                first = false;
            }

            for (Node node : nodes) {
                node.getTransform().getMatrix(MATRIX);

                MATRIX[4] += jitterSize * (0.5 - random.nextDouble());
                MATRIX[4] %= (width - node.getWidth());
                MATRIX[5] += jitterSize * (0.5 - random.nextDouble());
                MATRIX[5] %= (height - node.getHeight());

                node.setTransform(MATRIX);
            }
        }

    }
}
