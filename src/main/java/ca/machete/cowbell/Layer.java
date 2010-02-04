package ca.machete.cowbell;

public class Layer extends Node {

    @Override
    public void repaint() {
        for (Camera camera : getRoot().getCameras()) {
            camera.repaint();
        }
    }

}
