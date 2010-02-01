package ca.machete.cowbell;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class MockPaintContext extends PaintContext {

    public MockPaintContext(final int width, final int height) {
        super((Graphics2D) new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB).getGraphics());

        getGraphics().setClip(new Rectangle(0, 0, width, height));
    }
}
