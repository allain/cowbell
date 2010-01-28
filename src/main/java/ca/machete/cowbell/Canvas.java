package ca.machete.cowbell;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Canvas extends JComponent {
    private static final long serialVersionUID = 1L;

    private final Camera camera;

    public Canvas(Root root) {
        camera = new Camera();

        for (Connection connection : root.getLayerConnections()) {
            camera.addLayerConnection(connection);
        }

        root.connect(camera);
    }

    @Override
    public void paint(Graphics graphics) {
        long startTime = System.currentTimeMillis();
        camera.paint(new PaintContext((Graphics2D) graphics));
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
    }

    public Camera getCamera() {
        return camera;
    }
}
