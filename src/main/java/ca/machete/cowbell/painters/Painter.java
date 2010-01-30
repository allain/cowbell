package ca.machete.cowbell.painters;

import ca.machete.cowbell.Node;
import ca.machete.cowbell.PaintContext;

public interface Painter {

    void paint(Node node, PaintContext paintContext);

    Painter Null = new Painter() {
        @Override
        public void paint(final Node node, final PaintContext paintContext) {
        }
    };
}
