package ca.machete.cowbell.painters;

import static org.junit.Assert.assertEquals;
import java.awt.Color;
import org.junit.Test;
import ca.machete.cowbell.Node;

public class BorderPainterTest extends AbstractPainterTest {

    @Test
    public void paintUsesSizeOfNode() {
        Node node = new Node();
        node.setSize(5, 5);

        Painter borderPainter = new BorderPainter();

        borderPainter.paint(node, paintContext);

        // Check corners
        assertEquals(Color.black.getRGB(), image.getRGB(0, 0));
        assertEquals(Color.black.getRGB(), image.getRGB(4, 0));
        assertEquals(Color.black.getRGB(), image.getRGB(0, 4));
        assertEquals(Color.black.getRGB(), image.getRGB(4, 4));

        // Check Inside is left untouched
        assertEquals(Color.white.getRGB(), image.getRGB(1, 1));
        assertEquals(Color.white.getRGB(), image.getRGB(3, 1));
        assertEquals(Color.white.getRGB(), image.getRGB(1, 3));
        assertEquals(Color.white.getRGB(), image.getRGB(3, 3));
    }

}
