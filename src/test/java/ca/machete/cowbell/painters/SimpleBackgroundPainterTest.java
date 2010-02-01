package ca.machete.cowbell.painters;

import static org.junit.Assert.assertEquals;
import java.awt.Color;
import org.junit.Test;
import ca.machete.cowbell.Node;

public class SimpleBackgroundPainterTest extends AbstractPainterTest {

    @Test
    public void paintUsesSizeOfNode() {
        Node node = new Node();
        node.setSize(5, 5);

        Painter borderPainter = new SimpleBackgroundPainter(Color.BLACK);

        borderPainter.paint(node, paintContext);

        // Check inside
        assertEquals(Color.black.getRGB(), image.getRGB(0, 0));
        assertEquals(Color.black.getRGB(), image.getRGB(4, 0));
        assertEquals(Color.black.getRGB(), image.getRGB(0, 4));
        assertEquals(Color.black.getRGB(), image.getRGB(4, 4));
        assertEquals(Color.black.getRGB(), image.getRGB(2, 2));

        // Check Outside is left untouched
        assertEquals(Color.white.getRGB(), image.getRGB(0, 5));
        assertEquals(Color.white.getRGB(), image.getRGB(5, 5));
        assertEquals(Color.white.getRGB(), image.getRGB(5, 0));
    }

}
