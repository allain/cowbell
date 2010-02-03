package ca.machete.cowbell.events;


public class MouseListenerDispatcher extends AbstractDispatcher<IMouseListener> implements IMouseListener {
    @Override
    public void mouseClicked(final IMouseEvent mouseEvent) {
        for (IMouseListener listener : getListeners()) {
            listener.mouseClicked(mouseEvent);
        }
    }

    @Override
    public void mousePressed(IMouseEvent mouseEvent) {
        for (IMouseListener listener : getListeners()) {
            listener.mousePressed(mouseEvent);
        }
    }

    @Override
    public void mouseReleased(IMouseEvent mouseEvent) {
        for (IMouseListener listener : getListeners()) {
            listener.mouseReleased(mouseEvent);
        }
    }

    @Override
    public void mouseMoved(IMouseEvent mouseEvent) {
        for (IMouseListener listener : getListeners()) {
            listener.mouseMoved(mouseEvent);
        }
    }
}