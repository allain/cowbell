package ca.machete.cowbell.examples;

import javax.swing.JFrame;

import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.events.IMouseEvent;
import ca.machete.cowbell.events.MouseAdapter;

public class InteractionExample extends AbstractExample {

    static final int NODE_COUNT = 1000;

    public static void main(final String[] args) {
        InteractionExample example = new InteractionExample();

        example.run();
    }

    @Override
    public void run() {
        final Canvas canvas = buildSimpleCanvas();

        final Layer layer = canvas.getCamera().getLayer(0);

        addNodesToLayer(layer, NODE_COUNT);

        layer.setLayout(new RandomLayout());

        layer.addMouseListener(new MouseAdapter() {
            boolean buttonPressed = false;

            @Override
            public void mouseMoved(IMouseEvent mouseEvent) {
                if (!mouseEvent.getCoveredNodes().contains(layer) && buttonPressed) {
                    deleteNodesUnderMouse(mouseEvent);
                }
            }

            private void deleteNodesUnderMouse(IMouseEvent mouseEvent) {
                for (Node node : mouseEvent.getCoveredNodes()) {
                    node.getParent().removeChild(node);
                }
            }

            @Override
            public void mousePressed(IMouseEvent mouseEvent) {
                buttonPressed = true;
            }

            @Override
            public void mouseReleased(IMouseEvent mouseEvent) {
                buttonPressed = false;
            }

        });
        
        JFrame frame = wrapCanvasWithFrame(canvas, NODE_COUNT + " nodes to interact with. Just drag around.");

        frame.setVisible(true);
    }
}
