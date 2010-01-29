/**
 * 
 */
package ca.machete.cowbell.layouts;

import java.util.List;
import ca.machete.cowbell.Layout;
import ca.machete.cowbell.Node;

public final class SquareLayout implements Layout {

  @Override
  public void layout(final List<Node> nodes, final double width, final double height) {
    final int gridWidth = (int) Math.ceil(Math.sqrt(nodes.size()));

    double dx = width / gridWidth;
    double dy = height / gridWidth;

    int curCol = 0;
    int curRow = 0;

    for (Node node : nodes) {
      node.setTransform(1, 0, 0, 1, dx * curCol, dy * curRow);
      curCol++;
      if (curCol == gridWidth) {
        curCol = 0;
        curRow++;
      }
    }
  }
}
