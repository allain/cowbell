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

    final Random random = new Random();

    root.getScheduler().schedule(new InfiniteActivity() {

      private long lastZoomed = 0;

      @Override
      public void step() {
        final long currentTime = System.currentTimeMillis();

        if (currentTime - lastZoomed >= 1000) {
          scheduleRandomCameraAnimation(canvas, root, random, currentTime);

          lastZoomed = currentTime;
        }
      }

      private void scheduleRandomCameraAnimation(final Canvas canvas, final Root root, final Random random,
          final long currentTime) {
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
    });

    JFrame frame = wrapCanvasWithFrame(canvas, NODE_COUNT + " nodes with random camera transform");

    frame.setVisible(true);
  }
}
