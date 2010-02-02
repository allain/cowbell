package ca.machete.cowbell.events;

import java.awt.geom.Point2D;
import java.util.List;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Node;

public interface IMouseEvent {

    enum Type {
        Clicked
    }

    Type getType();

    Canvas getCanvas();

    Point2D getGlobalPoint();

    List<Node> getCoveredNodes();
}
