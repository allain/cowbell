package ca.machete.cowbell;


public interface CompositeNode extends Node {
    Connection connect(Node node);
    boolean disconnect(Connection connection);
    
    Iterable<Connection> getConnections();
}
