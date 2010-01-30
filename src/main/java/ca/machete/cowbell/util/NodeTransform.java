package ca.machete.cowbell.util;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class NodeTransform extends AffineTransform {

    private static final long serialVersionUID = 1L;

    public NodeTransform() {
        super();
    }
    
    public NodeTransform(double[] matrix) {
        super(matrix);
    }
    
    public Rectangle2D transform(final Rectangle2D fullBounds) {
        return transform(fullBounds, new Rectangle2D.Double());
    }

    public Rectangle2D transform(final Rectangle2D fullBounds, final Rectangle2D target) {
        Point2D topLeft = new Point2D.Double(fullBounds.getMinX(), fullBounds.getMinY());
        Point2D bottomRight = new Point2D.Double(fullBounds.getMaxX(), fullBounds.getMaxY());

        transform(topLeft, topLeft);
        transform(bottomRight, bottomRight);

        target.setRect(topLeft.getX(), topLeft.getY(), bottomRight.getX(), bottomRight.getY());

        return target;
    }

}
