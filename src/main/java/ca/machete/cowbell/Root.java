package ca.machete.cowbell;

import java.util.ArrayList;
import java.util.List;

public class Root extends SimpleCompositeNode {
    public Iterable<Connection> getLayerConnections() {
        List<Connection> layerConnections = new ArrayList<Connection>();
        
        for (Connection connection : getConnections()) {
            if (connection.getNode() instanceof Layer) {
                layerConnections.add(connection);
            }
        }
        
        return layerConnections;
    }
}
