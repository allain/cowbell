package ca.machete.cowbell;

import java.awt.Graphics2D;

public class PaintContext {
    private final Graphics2D graphics;

    public PaintContext(Graphics2D graphics) {
        assert graphics != null;
        this.graphics = graphics;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

}
