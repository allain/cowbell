package ca.machete.cowbell.examples;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import ca.machete.cowbell.BorderPainter;
import ca.machete.cowbell.Camera;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.PaintContext;
import ca.machete.cowbell.Painter;
import ca.machete.cowbell.Root;

public class WorstCaseExample {
    private static final int NODE_COUNT = 5000;
    private static final BorderPainter borderPainter = new BorderPainter();
    private static final Painter squarePainter = new Painter() {
        @Override
        public void paint(Node node, PaintContext paintContext) {
            Graphics2D graphics = paintContext.getGraphics();
            graphics.setPaint(Color.RED);
            
            graphics.fillRect(0, 0, (int) node.getWidth(), (int) node.getHeight());
        }
    };

    public static void main(String[] args) {

        Root root = new Root();
        Camera camera = new Camera();
        final Layer layer = new Layer();
        layer.addPainter(borderPainter);
        layer.setSize(600, 500);
        root.addChild(camera);
        root.addChild(layer);
        camera.addLayer(layer);

        addNodesToLayer(layer);

        layer.setLayout(new JitterLayout(3));

        root.getScheduler().schedule(new InvalidateLayoutActivity(layer));

        final Canvas canvas = buildCanvas(camera);

        JFrame frame = wrapCanvasWithFrame(canvas, NODE_COUNT + " nodes");

        frame.setVisible(true);
    }

    private static void addNodesToLayer(final Layer layer) {
        final double NODE_SIZE = Math.sqrt(600d * 500d / (NODE_COUNT * 1d));
        for (int nodeCount = 0; nodeCount < NODE_COUNT; nodeCount++) {
            Node node = new Node();
            node.addPainter(squarePainter);
            node.addPainter(borderPainter);
            node.setSize(NODE_SIZE, NODE_SIZE);
            layer.addChild(node);
        }
    }

    private static JFrame wrapCanvasWithFrame(final Canvas canvas, String title) {
        JFrame frame = new JFrame(title);
        frame.setContentPane(canvas);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    private static Canvas buildCanvas(Camera camera) {
        final Canvas canvas = new Canvas(camera);
        canvas.setPreferredSize(new Dimension(400, 400));
        canvas.setDoubleBuffered(true);
        return canvas;
    }
}
