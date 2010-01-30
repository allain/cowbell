package ca.machete.cowbell.layouts;

import java.util.List;
import ca.machete.cowbell.Node;

public interface Layout {

    Layout Null = new Layout() {

        @Override
        public void layout(final List<Node> nodes, final double width, final double height) {

        }

    };;

    void layout(List<Node> nodes, double width, double height);
}
