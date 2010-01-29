package ca.machete.cowbell;

import java.util.ArrayList;
import java.util.List;

import ca.machete.cowbell.activity.Scheduler;

public final class Root extends Node {
    private final Scheduler scheduler;
    
    public Root() {
        scheduler = new Scheduler();
    }
    
    public Iterable<Layer> getLayers() {
        List<Layer> layers = new ArrayList<Layer>();

        for (Node node : getChildren()) {
            if (node instanceof Layer) {
                layers.add((Layer)node);
            }
        }

        return layers;
    }
    
    public Iterable<Camera> getCameras() {
        List<Camera> cameras = new ArrayList<Camera>();

        for (Node node : getChildren()) {
            if (node instanceof Camera) {
                cameras.add((Camera)node);
            }
        }

        return cameras;
    }
    
    public Scheduler getScheduler() {
        return scheduler;
    }
    
    public Root getRoot() {
        return this;
    }
}
