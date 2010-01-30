/**
 * 
 */
package ca.machete.cowbell.layouts;

import java.util.List;
import ca.machete.cowbell.Node;

public final class SquareLayout implements Layout {

    @Override
    public void layout(final List<Node> nodes, final double width, final double height) {
        final int squareSize = (int) Math.ceil(Math.sqrt(nodes.size()));

        layout(nodes, width, height, squareSize);
    }

    public void layout(final List<Node> nodes, final double width, final double height, final int size) {
        double dx = width / size;
        double dy = height / size;

        int nodeCount = nodes.size();

        for (int curCol = 0; curCol < size; curCol++)
            for (int curRow = 0; curRow < size; curRow++) {
                int nodeIndex = curRow * size + curCol;
                if (nodeIndex < nodeCount) {
                    Node node = nodes.get(nodeIndex);
                    node.setTransform(1, 0, 0, 1, dx * curCol, dy * curRow);
                }
            }
    }
}
