package ca.machete.cowbell;

import java.awt.geom.AffineTransform;

public class Connection {
    private final Node node;
    private final AffineTransform transform;
    
    public Connection(Node node, AffineTransform transform) {
        this.node = node;
        this.transform = transform;
    }
    
    public Node getNode() {
        return node;
    }
    
    public AffineTransform getTransform() {
        return transform;
    }
    
    
}
