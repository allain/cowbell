package ca.machete.cowbell.activity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Scheduler {
    private final List<Activity> scheduled;
    private final List<Activity> running;

    public Scheduler() {

        scheduled = new LinkedList<Activity>();
        running = new LinkedList<Activity>();
    }

    public void schedule(Activity activity) {
        this.scheduled.add(activity);
    }

    public void kill(Activity activity) {
        this.scheduled.remove(activity);
        this.running.remove(activity);
    }

    public void tick() {
        final long currentTime = System.currentTimeMillis();

        activateScheduledActivities(currentTime);

        runActivatedActivities(currentTime);
    }

    private void runActivatedActivities(final long currentTime) {
        Iterator<Activity> ri = running.iterator();
        while (ri.hasNext()) {
            Activity activity = ri.next();
            long startTime = activity.getStartTime();
            if (!activity.step(currentTime - startTime)) {
                ri.remove();

                activity.stop();
            }
        }
    }

    private void activateScheduledActivities(final long currentTime) {
        Iterator<Activity> si = scheduled.iterator();
        while (si.hasNext()) {
            Activity activity = si.next();
            if (activity.getStartTime() <= currentTime) {
                si.remove();

                activity.start();
                running.add(activity);
            }
        }
    }
}
