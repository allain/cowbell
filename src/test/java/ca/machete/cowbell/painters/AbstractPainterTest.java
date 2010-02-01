package ca.machete.cowbell.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.junit.Before;
import ca.machete.cowbell.PaintContext;

public class AbstractPainterTest {

    protected BufferedImage image;

    private Graphics2D graphics;

    protected PaintContext paintContext;

    public AbstractPainterTest() {
        super();
    }

    @Before
    public void setupGraphics() {
        image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        graphics = image.createGraphics();
        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0, 0, 10, 10);

        paintContext = new PaintContext(graphics);
    }

}
