package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ca.machete.cowbell.layouts.Layout;
import ca.machete.cowbell.painters.Painter;
import ca.machete.cowbell.util.NodeTransform;

public class Node {
    private static final java.awt.geom.Point2D.Double POINT2D_ORIGIN = new Point2D.Double(0,0);

    private static final Point2D TMP_POINT = new Point2D.Double();
    
    private static final Rectangle2D TMP_RECTANGLE = new Rectangle2D.Double();

    /** Private singleton used in ALL nodes without children. */
    private static final List<Node> EMPTY_CHILDREN = Collections.<Node> emptyList();

    /** Private singleton used in ALL nodes without painters. */
    private static final List<Painter> EMPTY_PAINTERS = Collections.<Painter> emptyList();

    /** Painter responsible for painting children of a node. */
    public static final Painter CHILD_PAINTER = new Painter() {

        @Override
        public void paint(final Node node, final PaintContext paintContext) {
            for (Node child : node.children)
                child.paint(paintContext);
        }
    };

    protected List<Painter> painters;

    private Layout layout;

    private boolean invalidLayout;

    /** Arbitrary object that can be assigned to a node. */
    private Object model;

    protected final NodeTransform transform;

    private List<Node> children;

    private Node parent;

    private double width;

    private double height;

    private Rectangle2D fullBounds;

    public Node() {
        this(Layout.Null);
    }

    public Node(final Layout layout) {
        this.layout = (layout == null) ? Layout.Null : layout;

        invalidLayout = true;

        fullBounds = null;
        transform = new NodeTransform();
        children = EMPTY_CHILDREN;
        painters = EMPTY_PAINTERS;
    }

    public final void addChild(final Node node) {
        prepareNodeForChildren();

        if (node.parent == this) {
            children.remove(node);
        } else if (node.parent != null) {
            node.parent.removeChild(node);
        }

        children.add(node);
        node.parent = this;
    }

    private void prepareNodeForChildren() {
        if (children == EMPTY_CHILDREN) {
            children = new ArrayList<Node>();
            addPainter(painters.size(), CHILD_PAINTER);
        }
    }

    public final boolean removeChild(final Node node) {
        boolean removed = children.remove(node);

        if (children.isEmpty()) {
            children = EMPTY_CHILDREN;
            removePainter(CHILD_PAINTER);
        }

        return removed;
    }

    public final Iterable<Node> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public void paint(final PaintContext paintContext) {
        Graphics2D graphics = paintContext.getGraphics();

        if (!isVisibleOnGraphics(graphics))
            return;

        paintContext.pushTransform();

        graphics.transform(transform);

        for (Painter painter : painters)
            painter.paint(this, paintContext);

        paintContext.popTransform();
    }

    /**
     * Calculates whether the current node would be visible if drawn.
     * 
     * @param graphics
     *            Graphics in which we're testing this node's potential
     *            visibility
     * @return true if node would be visible if drawn
     */
    private boolean isVisibleOnGraphics(Graphics2D graphics) {
        return getFullBounds().intersects(graphics.getClip().getBounds2D());
    }

    public Rectangle2D getFullBounds() {
        if (fullBounds == null) {
            layout();
            
            fullBounds = collectChildrenBounds();
            
            fullBounds.add(transform.transform(POINT2D_ORIGIN, TMP_POINT));
            fullBounds.add(transform.transform(new Point2D.Double(width, height), TMP_POINT));
        }

        return fullBounds;
    }

    private Rectangle2D collectChildrenBounds() {
        final Rectangle2D fullBounds = new Rectangle2D.Double();

        for (Node node : children)
            fullBounds.add(transform.transform(node.getFullBounds(), TMP_RECTANGLE));

        return fullBounds;
    }

    public void layout() {
        if (invalidLayout) {
            for (Node node : children)
                node.layout();

            layout.layout(children, width, height);

            invalidLayout = false;
        }
    }

    public void addPainter(final int index, final Painter painter) {
        if (painters == EMPTY_PAINTERS)
            painters = new ArrayList<Painter>();

        painters.add(index, painter);
    }

    public void addPainter(final Painter painter) {
        addPainter(painters.size(), painter);
    }

    public boolean removePainter(final Painter painter) {
        boolean removed = painters.remove(painter);

        if (painters.isEmpty())
            painters = EMPTY_PAINTERS;

        return removed;
    }

    public int indexOfPainter(final Painter painter) {
        return painters.indexOf(painter);
    }

    public void setLayout(final Layout layout) {
        this.layout = layout;
    }

    public Layout getLayout() {
        return layout;
    }

    public AffineTransform getTransform() {
        return (AffineTransform) transform.clone();
    }

    public void setTransform(final AffineTransform transform) {
        this.transform.setTransform(transform);

        invalidateLayout();
    }

    public void setTransform(final double m00, final double m10, final double m01, final double m11, final double m02,
                    final double m12) {
        this.transform.setTransform(m00, m10, m01, m11, m02, m12);

        invalidateLayout();
    }

    public void setTransform(final double[] flatMatrix) {
        this.transform.setTransform(flatMatrix[0], flatMatrix[1], flatMatrix[2], flatMatrix[3], flatMatrix[4],
                        flatMatrix[5]);

        invalidateLayout();
    }

    public void invalidateLayout() {
        if (invalidLayout == false) {
            invalidLayout = true;
            fullBounds = null;
            if (parent != null)
                parent.invalidateLayout();
        }
    }

    public void setModel(final Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public Root getRoot() {
        if (parent == null)
            return null;

        return parent.getRoot();
    }

    public void setSize(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
