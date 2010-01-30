package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class CanvasTest {

    private Canvas canvas;

    @Before
    public void setupCanvas() {
        Camera camera = new Camera();
        this.canvas = new Canvas(camera);
    }

    @Test
    public void canvasShouldConstructWithoutChildrenByDefault() {
        assertEquals(0, canvas.getComponentCount());
    }
}
