package ca.machete.cowbell;

import java.awt.Graphics2D;
import ca.machete.cowbell.util.TransformStack;

public class PaintContext {

    private final TransformStack transformStack;

    private final Graphics2D graphics;

    public PaintContext(final Graphics2D graphics) {
        if (graphics == null)
            throw new IllegalArgumentException("graphics provided to PaintContext is null");

        this.graphics = graphics;

        this.transformStack = new TransformStack();
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void pushTransform() {
        transformStack.push(graphics.getTransform());
    }

    public void popTransform() {
        if (transformStack.peek() == null)
            throw new UnsupportedOperationException("Transform stack is empty");

        graphics.setTransform(transformStack.pop());
    }
}
