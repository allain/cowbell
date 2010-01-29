package ca.machete.cowbell;

import java.awt.Color;
import java.awt.Graphics2D;

public class BorderPainter implements Painter {

    @Override
    public void paint(Node node, PaintContext paintContext) {
        Graphics2D g = paintContext.getGraphics();
        g.setPaint(Color.BLACK);
        g.drawRect(0, 0, (int) node.getWidth(), (int) node.getHeight());
    }

}
