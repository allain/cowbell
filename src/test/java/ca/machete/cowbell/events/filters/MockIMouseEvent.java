package ca.machete.cowbell.events.filters;

import java.awt.geom.Point2D;
import java.util.List;
import ca.machete.cowbell.Camera;
import ca.machete.cowbell.Canvas;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.Root;
import ca.machete.cowbell.events.IMouseEvent;


public class MockIMouseEvent implements IMouseEvent {

    private Type type;
    private List<Node> coveredNodes;
    private int x;
    private int y;
    private Canvas canvas;

    public MockIMouseEvent(Type type, List<Node> nodes, int x, int y) {
        this.type = type;
        this.coveredNodes = nodes;
        this.x = x;
        this.y = y;
        Root root = new Root();
        Camera camera = new Camera();
        root.addChild(camera);
        this.canvas = new Canvas(camera);
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public List<Node> getCoveredNodes() {
        return coveredNodes;
    }

    @Override
    public Point2D getGlobalPoint() {
        return new Point2D.Double(x, y);
    }

    @Override
    public Type getType() {
        return type;
    }

}
