package ca.machete.cowbell.examples;

import java.awt.geom.AffineTransform;
import java.util.Random;
import javax.swing.JFrame;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Layer;
import ca.machete.cowbell.Root;
import ca.machete.cowbell.activity.InfiniteActivity;

public class CameraZoomExample extends AbstractExample {

    static final int NODE_COUNT = 10000;

    public static void main(final String[] args) {
        CameraZoomExample example = new CameraZoomExample();

        example.run();
    }

    @Override
    public void run() {
        final Canvas canvas = buildSimpleCanvas();

        final Layer layer = canvas.getCamera().getLayer(0);
        final Root root = canvas.getCamera().getRoot();

        addNodesToLayer(layer, NODE_COUNT);

        layer.setLayout(new RandomLayout());

        root.getScheduler().schedule(new RandomCameraScheduler(canvas));

        JFrame frame = wrapCanvasWithFrame(canvas, NODE_COUNT + " nodes with random camera transform");

        frame.setVisible(true);
    }

    private final class RandomCameraScheduler extends InfiniteActivity {

        private final Random random;

        private final Canvas canvas;

        private long lastZoomed = 0;

        private RandomCameraScheduler(final Canvas canvas) {
            this.random = new Random();
            this.canvas = canvas;
        }

        @Override
        public void step() {
            final long currentTime = System.currentTimeMillis();

            if (currentTime - lastZoomed >= 1000) {
                scheduleRandomCameraAnimation(canvas, currentTime);

                lastZoomed = currentTime;
            }
        }

        private void scheduleRandomCameraAnimation(final Canvas canvas, final long currentTime) {
            double randomScale = random.nextDouble() * 2d + 1d;
            double randomX = random.nextDouble() * 500;
            double randomY = random.nextDouble() * 500;

            AffineTransform newTransform = createRandomCameraTransform(randomScale, randomX, randomY);

            canvas.getCamera().animateViewToTransform(newTransform, 1000);
        }

        private AffineTransform createRandomCameraTransform(final double scale, final double tx, final double ty) {
            AffineTransform newTransform = new AffineTransform();
            newTransform.translate(-tx, -ty);
            newTransform.scale(scale, scale);
            return newTransform;
        }
    }
}
