package ca.machete.cowbell.events;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Node;

public class AwtMouseEvent implements IMouseEvent {

    private final Type type;

    private final Canvas canvas;

    private final Point2D globalPoint;

    private final List<Node> coveredNodes;

    public AwtMouseEvent(java.awt.event.MouseEvent awtEvent, final List<Node> coveredNodes) {
        if ((awtEvent.getID() == MouseEvent.MOUSE_CLICKED)) {
            type = Type.Clicked;
        } else {
            throw new IllegalArgumentException("MouseEvent is of unrecognized typed.");
        }

        canvas = (Canvas) awtEvent.getComponent();
        
        globalPoint = canvas.getCamera().viewToGlobal(new Point2D.Double(awtEvent.getX(), awtEvent.getY()));
        
        this.coveredNodes = coveredNodes;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public Point2D getGlobalPoint() {
        return globalPoint;
    }

    @Override
    public List<Node> getCoveredNodes() {
        return coveredNodes;
    }

}
