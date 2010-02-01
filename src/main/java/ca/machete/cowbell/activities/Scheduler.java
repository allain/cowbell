package ca.machete.cowbell.activities;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scheduler {

    private final List<Activity> pendingActivities;

    private final List<Activity> activeActivites;

    public Scheduler() {
        pendingActivities = new LinkedList<Activity>();
        activeActivites = new LinkedList<Activity>();
    }

    public void schedule(final Activity activity) {
        this.pendingActivities.add(activity);
    }

    public void kill(final Activity activity) {
        this.pendingActivities.remove(activity);
        this.activeActivites.remove(activity);
    }

    public void tick() {
        final long currentTime = System.currentTimeMillis();

        activatePendingActivities(currentTime);

        runActivatedActivities(currentTime);
    }

    private void activatePendingActivities(final long currentTime) {
        Iterator<Activity> si = pendingActivities.iterator();
        while (si.hasNext()) {
            Activity activity = si.next();
            if (activity.getStartTime() <= currentTime) {
                si.remove();

                activity.start();
                activeActivites.add(activity);
            }
        }
    }

    private void runActivatedActivities(final long currentTime) {
        Iterator<Activity> ri = activeActivites.iterator();
        while (ri.hasNext()) {
            Activity activity = ri.next();
            long startTime = activity.getStartTime();
            long elapsedTime = currentTime - startTime;
            if (!activity.step(elapsedTime)) {
                ri.remove();

                activity.stop();
            }
        }
    }

    public Collection<Activity> getPendingActivities() {
        return Collections.unmodifiableCollection(pendingActivities);
    }

    public Collection<Activity> getActiveActivities() {
        return Collections.unmodifiableCollection(activeActivites);
    }
}
