package ca.machete.cowbell.util;

import org.junit.Test;

public class TransformStackTest {

    @Test(expected = IllegalArgumentException.class)
    public void pushingNullStackThrowsException() {
        TransformStack stack = new TransformStack();
        stack.push(null);
    }
}
