package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Camera extends SimpleCompositeNode {
    private AffineTransform viewTransform;
    private List<Connection> layerConnections;

    public Camera() {
        layerConnections = new ArrayList<Connection>();
        viewTransform = new AffineTransform();
    }
    
    @Override
    public void paint(PaintContext paintContext) {
        paintLayers(paintContext);
        super.paint(paintContext);
    }

    private void paintLayers(PaintContext paintContext) {
        Graphics2D graphics = paintContext.getGraphics();
        final AffineTransform startTransform = graphics.getTransform();
        
        graphics.transform(viewTransform);
        final AffineTransform startViewTransform = graphics.getTransform();
        
        for (Connection connection : layerConnections) {   
            graphics.transform(connection.getTransform());
            connection.getNode().paint(paintContext);
            graphics.setTransform(startViewTransform);
        }
        
        graphics.setTransform(startTransform);
    }

    public void addLayerConnection(Connection connection) {
        layerConnections.add(connection);
    }

    public void setViewTransform(AffineTransform transform) {
        viewTransform = transform;
    }

}
