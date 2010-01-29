package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import ca.machete.cowbell.activity.Activity;
import ca.machete.cowbell.examples.CameraViewTransformActivity;

public class Camera extends Node {

  private AffineTransform viewTransform;

  private final List<Layer> layers;

  private static final Painter LAYER_PAINTER = new Painter() {

    @Override
    public void paint(final Node node, final PaintContext paintContext) {
      Camera camera = (Camera) node;
      camera.paintLayers(paintContext);
    }
  };

  public Camera() {
    layers = new ArrayList<Layer>();
    viewTransform = new AffineTransform();

    addPainter(0, LAYER_PAINTER);
  }

  private void paintLayers(final PaintContext paintContext) {
    Graphics2D graphics = paintContext.getGraphics();
    final AffineTransform startTransform = graphics.getTransform();

    graphics.transform(viewTransform);

    for (Layer layer : layers)
      layer.paint(paintContext);

    graphics.setTransform(startTransform);
  }

  @Override
  public final void paint(final PaintContext paintContext) {
    Graphics2D graphics = paintContext.getGraphics();

    final AffineTransform startTransform = graphics.getTransform();
    graphics.transform(transform);

    for (Painter painter : painters)
      painter.paint(this, paintContext);

    graphics.setTransform(startTransform);
  }

  public AffineTransform getViewTransform() {
    return viewTransform;
  }

  public void addLayer(final Layer layer) {
    layers.add(layer);

  }

  public void setViewTransform(final AffineTransform transform) {
    viewTransform = transform;
  }

  public void setViewTransform(final double m00, final double m10, final double m01, final double m11,
      final double m02, final double m12) {
    this.viewTransform.setTransform(m00, m10, m01, m11, m02, m12);
  }

  public void setViewTransform(final double[] matrix) {
    this.viewTransform.setTransform(matrix[0], matrix[1], matrix[2], matrix[3], matrix[4], matrix[5]);

  }

  public Layer getLayer(final int layerIndex) {
    return layers.get(layerIndex);
  }

  public void animateViewToTransform(AffineTransform targetTransform, int duration) {
    long currentTime = System.currentTimeMillis();
    
    Activity activity = new CameraViewTransformActivity(this, targetTransform, currentTime, duration);
    
    getRoot().getScheduler().schedule(activity);
  }
}
