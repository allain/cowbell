package ca.machete.cowbell.examples;

import ca.machete.cowbell.Node;
import ca.machete.cowbell.activity.InfiniteActivity;

public class InvalidateLayoutActivity extends InfiniteActivity {

  private final Node node;

  public InvalidateLayoutActivity(final Node node) {
    this.node = node;
  }

  @Override
  public void step() {
    node.invalidateLayout();
  }

}
