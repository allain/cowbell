package ca.machete.cowbell;

public interface Painter {

  void paint(Node node, PaintContext paintContext);

  Painter Null = new Painter() {

    @Override
    public void paint(final Node node, final PaintContext paintContext) {
    }
  };
}
