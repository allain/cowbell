package ca.machete.cowbell;

import java.awt.geom.AffineTransform;

public class Connection {

  private final Node node;

  private final AffineTransform transform;

  public Connection(final Node node, final AffineTransform transform) {
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
