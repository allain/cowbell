package ca.machete.cowbell.examples;

import java.util.List;
import java.util.Random;

import ca.machete.cowbell.Node;

public class JitterLayout extends RandomLayout {
    private boolean first = true;
    private Random random = new Random();
    private double jitterSize;

    public JitterLayout(double jitterSize) {
        this.jitterSize = jitterSize;
    }

    @Override
    public void layout(List<Node> nodes, double width, double height) {
        if (first) {
            super.layout(nodes, width, height);
            first = false;
        }

        for (Node node : nodes) {
            double newX = node.getTransform().getTranslateX() + jitterSize * (0.5 - random.nextDouble());
            double newY = node.getTransform().getTranslateY() + jitterSize * (0.5 - random.nextDouble());

            double scale = 0.5 - random.nextDouble();
            node.setTransform(1 + scale*0.25, 0, 0, 1 + scale*0.25, newX % (width - node.getWidth()), newY % (height - node.getHeight()));
        }
    }

}
