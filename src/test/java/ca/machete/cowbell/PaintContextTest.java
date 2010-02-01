package ca.machete.cowbell;

import static org.junit.Assert.assertSame;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.junit.Test;

public class PaintContextTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorShoudThrowExceptionOnNullGraphics2D() {
        new PaintContext(null);
    }

    @Test
    public void constructorPersistsGrpahics2D() {
        Graphics2D graphics = buildGraphics2D();
        PaintContext context = new PaintContext(graphics);
        assertSame(graphics, context.getGraphics());
    }

    private Graphics2D buildGraphics2D() {
        return (Graphics2D) new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB).getGraphics();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void popTransformFailsWhenNoTransformPushed() {
        PaintContext context = new PaintContext(buildGraphics2D());
        context.popTransform();
    }

    @Test
    public void popTransformWorksWhenTransformHasBeenPushed() {
        PaintContext context = new PaintContext(buildGraphics2D());

        context.pushTransform();
        context.popTransform();
    }
}
