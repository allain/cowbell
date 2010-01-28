package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SimpleCompositeNode implements CompositeNode {
    private final List<Connection> connections;

    public SimpleCompositeNode() {
        connections = new LinkedList<Connection>();
    }

    @Override
    public final Connection connect(Node node) {
        Connection connection = new Connection(node, new AffineTransform());
        connections.add(connection);
        return connection;
    }

    @Override
    public final boolean disconnect(Connection connection) {
        return connections.remove(connection);
    }

    @Override
    public final Iterable<Connection> getConnections() {
        return Collections.unmodifiableCollection(connections);
    }

    @Override
    public void paint(PaintContext paintContext) {
        Graphics2D graphics = paintContext.getGraphics();
        
        final AffineTransform startTransform = graphics.getTransform();
        
        for (Connection connection : connections) {
            graphics.transform(connection.getTransform());
            connection.getNode().paint(paintContext);
            
            graphics.setTransform(startTransform);
        }
    }

}
