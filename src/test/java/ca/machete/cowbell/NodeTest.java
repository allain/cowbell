package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Rectangle2D;

import org.junit.Test;


public class NodeTest {
    @Test
    public void fullBoundsShouldBeSizeWhenNoChildren() {
        Node node = new Node();
        node.setSize(10, 20);
        Rectangle2D bounds = node.getFullBounds();
        assertEquals(0, bounds.getMinX(), 0.0001);
        assertEquals(0, bounds.getMinY(), 0.0001);
        assertEquals(10, bounds.getMaxX(), 0.0001);
        assertEquals(20, bounds.getMaxY(), 0.0001);
    }
    
    @Test
    public void fullBoundsIncludesChildEvenWhenNoSizeItself() {
        Node node = new Node();
        Node child = new Node();
        child.setSize(10, 20);
        node.addChild(child);
        
        Rectangle2D bounds = node.getFullBounds();
        assertEquals(0, bounds.getMinX(), 0.0001);
        assertEquals(0, bounds.getMinY(), 0.0001);
        assertEquals(10, bounds.getMaxX(), 0.0001);
        assertEquals(20, bounds.getMaxY(), 0.0001);
    }
    
    @Test
    public void fullBoundsIncludesChildWhenChildTranslated() {
        Node node = new Node();
        Node child = new Node();
        child.setTransform(1, 0, 0, 1, 10, 10);
        child.setSize(10, 20);
        node.addChild(child);
        
        Rectangle2D bounds = node.getFullBounds();
        assertEquals(0, bounds.getMinX(), 0.0001);
        assertEquals(0, bounds.getMinY(), 0.0001);
        assertEquals(20, bounds.getMaxX(), 0.0001);
        assertEquals(30, bounds.getMaxY(), 0.0001);
    }
}
