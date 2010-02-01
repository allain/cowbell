package ca.machete.cowbell;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import ca.machete.cowbell.activities.Activity;
import ca.machete.cowbell.activities.InfiniteActivity;
import ca.machete.cowbell.activities.Scheduler;

public class SchedulerTest {

    @Test
    public void killingAnActivityRemovesItFromPendingAndActive() {
        Scheduler scheduler = new Scheduler();
        Activity activity = new InfiniteActivity() {

            @Override
            public void step() {
            }
        };
        scheduler.schedule(activity);
        scheduler.kill(activity);
        assertEquals(0, scheduler.getActiveActivities().size());
        assertEquals(0, scheduler.getPendingActivities().size());
    }

    @Test
    public void pendingActivityBecomeActiveWhenTheirStartTimeIsReached() {
        Scheduler scheduler = new Scheduler();
        Activity activity = new InfiniteActivity() {

            @Override
            public void step() {
            }
        };
        scheduler.schedule(activity);
        assertTrue(scheduler.getPendingActivities().contains(activity));
        assertFalse(scheduler.getActiveActivities().contains(activity));
        scheduler.tick();
        assertFalse(scheduler.getPendingActivities().contains(activity));
        assertTrue(scheduler.getActiveActivities().contains(activity));
    }

    @Test
    public void activitiesThatReturnFalseWhenSteppingAreKilled() {
        Scheduler scheduler = new Scheduler();
        Activity activity = new InstantenousMockActivity();
        scheduler.schedule(activity);
        assertTrue(scheduler.getPendingActivities().contains(activity));
        assertFalse(scheduler.getActiveActivities().contains(activity));
        scheduler.tick();
        assertFalse(scheduler.getPendingActivities().contains(activity));
        assertFalse(scheduler.getActiveActivities().contains(activity));
    }

    private final class InstantenousMockActivity implements Activity {

        @Override
        public long getStartTime() {
            return 0;
        }

        @Override
        public void start() {
        }

        @Override
        public boolean step(final long timeEllapsed) {
            return false;
        }

        @Override
        public void stop() {
        }
    }
}
