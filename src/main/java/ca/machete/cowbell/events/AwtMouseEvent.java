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

    public AwtMouseEvent(final java.awt.event.MouseEvent awtEvent) {
        type = extractTypeFromEvent(awtEvent);

        canvas = (Canvas) awtEvent.getComponent();

        globalPoint = canvas.getCamera().viewToGlobal(new Point2D.Double(awtEvent.getX(), awtEvent.getY()));

        this.coveredNodes = canvas.getNodesAt(awtEvent.getX(), awtEvent.getY());
    }

    private final Type extractTypeFromEvent(final java.awt.event.MouseEvent awtEvent) {
        switch (awtEvent.getID()) {
            case MouseEvent.MOUSE_CLICKED:
                return Type.Click;
            case MouseEvent.MOUSE_PRESSED:
                return Type.Press;
            case MouseEvent.MOUSE_RELEASED:
                return Type.Release;
            default:
                throw new IllegalArgumentException("MouseEvent is of unrecognized typed.");
        }
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
