package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import ca.machete.cowbell.activities.Activity;
import ca.machete.cowbell.activities.CameraViewTransformActivity;
import ca.machete.cowbell.painters.Painter;

public class Camera extends Node {

    private final AffineTransform viewTransform;

    private final List<Layer> layers;

    /**
     * Handles the painting of layers attached to a camera.
     */
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
        paintContext.pushTransform();

        graphics.transform(viewTransform);

        for (Layer layer : layers) {
            layer.paint(paintContext);
        }

        paintContext.popTransform();
    }

    @Override
    public void paint(final PaintContext paintContext) {
        Graphics2D graphics = paintContext.getGraphics();

        paintContext.pushTransform();

        graphics.transform(transform);

        for (Painter painter : painters) {
            painter.paint(this, paintContext);
        }

        paintContext.popTransform();
        
        needsPainting = false;
    }

    public AffineTransform getViewTransform() {
        return (AffineTransform) viewTransform.clone();
    }

    public void addLayer(final Layer layer) {
        layers.add(layer);
    }

    public void setViewTransform(final AffineTransform transform) {
        viewTransform.setTransform(transform);
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

    public void animateViewToTransform(final AffineTransform targetTransform, final int duration) {
        long currentTime = System.currentTimeMillis();

        Activity activity = new CameraViewTransformActivity(this, targetTransform, currentTime, duration);

        getRoot().getScheduler().schedule(activity);
    }

    public List<Node> getNodesAt(final Point2D parentPoint) {
        List<Node> result = new ArrayList<Node>();

        Point2D localPoint = transform.transform(parentPoint, null);
        fetchDescendantsAt(this, localPoint, result);

        if (result.isEmpty()) {
            Point2D layerPoint = viewToGlobal(parentPoint);

            for (Layer layer : layers) {
                if (layer.getFullBounds().contains(layerPoint)) {
                    fetchNodesAt(layer, layerPoint, result);
                }
            }
        }

        return result;
    }

    private void fetchNodesAt(final Node parent, final Point2D localPoint, final List<Node> result) {
        if (!fetchDescendantsAt(parent, localPoint, result)) {
            result.add(parent);
        }
    }

    private boolean fetchDescendantsAt(final Node parent, final Point2D localPoint, final List<Node> result) {
        final int startSize = result.size();

        for (Node child : parent.getChildren()) {
            if (child.getFullBounds().contains(localPoint)) {
                Point2D childPoint = child.parentToLocal(localPoint);
                fetchNodesAt(child, childPoint, result);
            }
        }

        return result.size() != startSize;
    }

    public Point2D viewToGlobal(final Point2D viewPoint) {
        try {
            return viewTransform.inverseTransform(viewPoint, null);
        } catch (NoninvertibleTransformException e) {
            return null;
        }
    }

    public boolean needsPainting() {
        return needsPainting;
    }
}
