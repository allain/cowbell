package ca.machete.cowbell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import ca.machete.cowbell.activities.Scheduler;

public final class Root extends Node {

    private final Scheduler scheduler;

    private final Timer masterTimer;

    public Root() {
        scheduler = new Scheduler();

        masterTimer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                scheduler.tick();
            }
        });
        masterTimer.setRepeats(true);
        masterTimer.start();
    }

    public Iterable<Layer> getLayers() {
        List<Layer> layers = new ArrayList<Layer>();

        for (Node node : getChildren())
            if (node instanceof Layer) {
                layers.add((Layer) node);
            }

        return layers;
    }

    public Iterable<Camera> getCameras() {
        List<Camera> cameras = new ArrayList<Camera>();

        for (Node node : getChildren())
            if (node instanceof Camera) {
                cameras.add((Camera) node);
            }

        return cameras;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public Root getRoot() {
        return this;
    }
}
