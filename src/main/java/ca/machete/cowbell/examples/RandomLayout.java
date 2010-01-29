/**
 * 
 */
package ca.machete.cowbell.examples;

import java.util.List;
import java.util.Random;

import ca.machete.cowbell.Layout;
import ca.machete.cowbell.Node;

public class RandomLayout implements Layout {
    private Random random = new Random();

    @Override
    public void layout(List<Node> nodes, double width, double height) {
        for (Node node : nodes) {
            node.setTransform(1, 0, 0, 1, random.nextDouble()*width, random.nextDouble()*height);
        }
    }
}