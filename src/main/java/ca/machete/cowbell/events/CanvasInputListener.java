package ca.machete.cowbell.events;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import ca.machete.cowbell.Node;


public class CanvasInputListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        AwtMouseEvent awtEvent = new AwtMouseEvent(e);
        for (final Node node : awtEvent.getCoveredNodes()) {
            Node currentNode = node;
            while (currentNode != null) {
                currentNode.getMouseListenerDispatcher().mouseClicked(awtEvent);
                currentNode = currentNode.getParent();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        AwtMouseEvent awtEvent = new AwtMouseEvent(e);
        for (final Node node : awtEvent.getCoveredNodes()) {
            Node currentNode = node;
            while (currentNode != null) {
                currentNode.getMouseListenerDispatcher().mousePressed(awtEvent);
                currentNode = currentNode.getParent();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        AwtMouseEvent awtEvent = new AwtMouseEvent(e);
        for (final Node node : awtEvent.getCoveredNodes()) {
            Node currentNode = node;
            while (currentNode != null) {
                currentNode.getMouseListenerDispatcher().mouseReleased(awtEvent);
                currentNode = currentNode.getParent();
            }
        }
    }

}
