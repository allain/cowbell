/**
 * 
 */
package ca.machete.cowbell.painters;

import java.awt.Graphics2D;
import java.awt.Paint;
import ca.machete.cowbell.Node;
import ca.machete.cowbell.PaintContext;
import ca.machete.cowbell.Painter;

public class SimpleBackgroundPainter implements Painter {

  private final Paint paint;

  public SimpleBackgroundPainter(final Paint paint) {
    this.paint = paint;
  }

  @Override
  public void paint(final Node node, final PaintContext paintContext) {
    Graphics2D graphics = paintContext.getGraphics();

    graphics.setPaint(paint);

    graphics.fillRect(0, 0, (int) node.getWidth(), (int) node.getHeight());
  }
}
