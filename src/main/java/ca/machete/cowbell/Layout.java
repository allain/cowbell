package ca.machete.cowbell;

import java.util.List;

public interface Layout {
    Layout Null = new Layout() {

        @Override
        public void layout(List<Node> nodes, double width, double height) {

        }

    };;

    void layout(List<Node> nodes, double width, double height);
}