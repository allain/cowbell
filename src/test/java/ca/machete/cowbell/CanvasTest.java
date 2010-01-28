package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class CanvasTest {
    private Canvas canvas;
    private Root root;

    @Before
    public void setupCanvas() {
        this.root = new Root();
        this.root.connect(new Layer());
        this.canvas = new Canvas(root);
    }
    
    @Test
    public void canvasShouldConstructWithoutChildrenByDefault() {
        assertEquals(0, canvas.getComponentCount());
    }
}
