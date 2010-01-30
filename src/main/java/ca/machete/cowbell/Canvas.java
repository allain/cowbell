package ca.machete.cowbell;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Canvas extends JComponent {

    private static final long serialVersionUID = 1L;

    private final Camera camera;

    private final Timer masterTimer;

    public Canvas(final Camera camera) {
        this.camera = camera;
        masterTimer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                camera.getRoot().getScheduler().tick();
                Canvas.this.repaint();
            }

        });
        masterTimer.setRepeats(true);
        masterTimer.start();
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
}
