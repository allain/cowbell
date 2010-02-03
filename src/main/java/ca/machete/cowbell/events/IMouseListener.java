package ca.machete.cowbell.events;


public interface IMouseListener {
    void mouseClicked(IMouseEvent mouseEvent);
    void mousePressed(IMouseEvent mouseEvent);
    void mouseReleased(IMouseEvent mouseEvent);
    
    
    final IMouseListener Null = new MouseAdapter();
    
}
