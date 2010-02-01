package ca.machete.cowbell.painters;

import ca.machete.cowbell.Node;
import ca.machete.cowbell.PaintContext;

public interface Painter {

    void paint(Node node, PaintContext paintContext);
}
