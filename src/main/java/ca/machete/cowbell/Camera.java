package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Camera extends Node {
    private AffineTransform viewTransform;
    private List<Layer> layers;

    public Camera() {
        layers = new ArrayList<Layer>();
        viewTransform = new AffineTransform();

        addPainter(0, LAYER_PAINTER);
    }

    private void paintLayers(PaintContext paintContext) {
        Graphics2D graphics = paintContext.getGraphics();
        final AffineTransform startTransform = graphics.getTransform();

        graphics.transform(viewTransform);

        for (Layer layer : layers) {
            layer.paint(paintContext);
        }

        graphics.setTransform(startTransform);
    }

    public final void paint(PaintContext paintContext) {
        Graphics2D graphics = paintContext.getGraphics();

        final AffineTransform startTransform = graphics.getTransform();
        graphics.transform(transform);

        for (Painter painter : painters) {
            painter.paint(this, paintContext);
        }

        graphics.setTransform(startTransform);
    }

    public void setViewTransform(AffineTransform transform) {
        viewTransform = transform;
    }

    public void addLayer(Layer layer) {
        layers.add(layer);

    }

    private static final Painter LAYER_PAINTER = new Painter() {
        @Override
        public void paint(Node node, PaintContext paintContext) {
            Camera camera = (Camera) node;
            camera.paintLayers(paintContext);
        }
    };
}
