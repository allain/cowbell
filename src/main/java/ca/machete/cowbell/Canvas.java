package ca.machete.cowbell;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;
import javax.swing.JComponent;
import ca.machete.cowbell.activities.InfiniteActivity;
import ca.machete.cowbell.events.CanvasInputListener;

public class Canvas extends JComponent {

    private static final long serialVersionUID = 1L;

    private final Camera camera;

    public Canvas(final Camera camera) {
        if (camera == null)
            throw new IllegalArgumentException("camera provided to canvas is null");

        if (camera.getRoot() == null)
            throw new IllegalArgumentException("Attemptingto build Canvas using an unattached camera");

        this.camera = camera;
        this.camera.getRoot().getScheduler().schedule(new InfiniteActivity() {

            @Override
            public void step() {
                repaint();
            }

        });

        installListeners();
    }

    private final void installListeners() {
        CanvasInputListener listener = new CanvasInputListener();
        addMouseListener(listener);
    }

    @Override
    public void paint(final Graphics graphics) {
        long startTime = System.currentTimeMillis();
        graphics.clipRect(0, 0, getWidth(), getHeight());
        camera.paint(new PaintContext((Graphics2D) graphics));
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    public Camera getCamera() {
        return camera;
    }

    public List<Node> getNodesAt(final int x, final int y) {
        return camera.getNodesAt(new Point2D.Double(x, y));
    }
}
