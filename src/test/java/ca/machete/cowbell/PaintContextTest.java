package ca.machete.cowbell;

import org.junit.Test;


public class PaintContextTest {
  @Test(expected=AssertionError.class)
  public void constructorShoudThrowExceptionOnNullGraphics2D() {
      new PaintContext(null);
  }
}
