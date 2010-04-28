package ca.machete.cowbell.util;

import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;

public class TransformStack {

    private final List<AffineTransform> transforms;

    public TransformStack() {
        transforms = new LinkedList<AffineTransform>();
    }

    public void push(final AffineTransform transform) {
        if (transform == null)
            throw new IllegalArgumentException("Null transform invalid on stack");

        transforms.add(transform);
    }

    public AffineTransform pop() {
        return transforms.remove(transforms.size() - 1);
    }

    public AffineTransform peek() {
        if (transforms.isEmpty())
            return null;

        return transforms.get(transforms.size() - 1);
    }
}
