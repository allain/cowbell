/**
 * 
 */
package ca.machete.cowbell.examples;

import java.util.List;
import java.util.Random;

import ca.machete.cowbell.Node;
import ca.machete.cowbell.layouts.Layout;

public class RandomLayout implements Layout {

    private final Random random = new Random();

    @Override
    public void layout(final List<Node> nodes, final double width, final double height) {
        for (Node node : nodes)
            node.setTransform(1, 0, 0, 1, random.nextDouble() * (width - node.getWidth()), random.nextDouble()
                            * (height - node.getHeight()));
    }
}
