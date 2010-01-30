package ca.machete.cowbell.examples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import ca.machete.cowbell.Camera;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.Root;
import ca.machete.cowbell.painters.BorderPainter;
import ca.machete.cowbell.painters.Painter;
import ca.machete.cowbell.painters.SimpleBackgroundPainter;

public abstract class AbstractExample implements Runnable {

    protected static final BorderPainter borderPainter = new BorderPainter();

    public AbstractExample() {
        super();
    }

    protected final void addNodesToLayer(final Layer layer, final int nodeCount) {
        final double NODE_SIZE = Math.sqrt(600d * 500d / (nodeCount * 1d));
        for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
            Node node = new Node();
            node.addPainter(getBackgroundPainter(nodeIndex));
            node.addPainter(borderPainter);
            node.setSize(NODE_SIZE, NODE_SIZE);
            layer.addChild(node);
        }
    }

    private final Painter[] nodeBackgroundPainters = new Painter[] { new SimpleBackgroundPainter(Color.RED),
                    new SimpleBackgroundPainter(Color.BLUE), new SimpleBackgroundPainter(Color.YELLOW),
                    new SimpleBackgroundPainter(Color.GREEN), new SimpleBackgroundPainter(Color.ORANGE) };

    private Painter getBackgroundPainter(final int nodeIndex) {
        return nodeBackgroundPainters[nodeIndex % nodeBackgroundPainters.length];
    }

    protected JFrame wrapCanvasWithFrame(final Canvas canvas, final String title) {
        JFrame frame = new JFrame(title);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JLabel(title), BorderLayout.NORTH);
        frame.getContentPane().add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    protected Canvas buildCanvas(final Camera camera) {
        final Canvas canvas = new Canvas(camera);
        canvas.setPreferredSize(new Dimension(400, 400));
        canvas.setDoubleBuffered(true);
        return canvas;
    }

    protected Canvas buildSimpleCanvas() {
        Root root = new Root();
        Camera camera = new Camera();
        final Layer layer = new Layer();
        layer.addPainter(new SimpleBackgroundPainter(Color.WHITE));
        layer.addPainter(WorstCaseExample.borderPainter);
        layer.setSize(600, 500);
        root.addChild(camera);
        root.addChild(layer);
        camera.addLayer(layer);

        return new Canvas(camera);
    }

    public abstract void run();
}
