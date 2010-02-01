package ca.machete.cowbell.activities;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TimedActivityTest {

    double lastRatioCompletedValue;

    @Test
    public void timedActivityPersistsStartTimeAndDuration() {
        TimedActivity activity = new TimedActivity(1, 2) {

            @Override
            public void step(final double ratioCompleted) {
            }

        };

        assertEquals(1, activity.getStartTime());
        assertEquals(2, activity.getDuration());
    }

    @Test
    public void callingStartInvokesStepWithAppropriateParams() {
        TimedActivity activity = new TimedActivity(1, 2) {

            @Override
            public void step(final double ratioCompleted) {
                lastRatioCompletedValue = ratioCompleted;
            }

        };

        lastRatioCompletedValue = -1;
        activity.start();
        assertEquals(0d, lastRatioCompletedValue, 0.001);
    }

    @Test
    public void callingStopInvokesStepWithAppropriateParams() {
        TimedActivity activity = new MockActivity(1, 2);

        lastRatioCompletedValue = -1;
        activity.stop();
        assertEquals(1d, lastRatioCompletedValue, 0.001);
    }

    @Test
    public void noEllapsedTimeIsInterpretedAs0Ratio() {
        TimedActivity activity = new MockActivity(0, 200);

        lastRatioCompletedValue = -1;
        activity.step(0);
        assertEquals(0d, lastRatioCompletedValue, 0.001);
    }

    @Test
    public void fullEllapsedTimeIsInterpretedAs1Ratio() {
        TimedActivity activity = new MockActivity(0, 200);

        lastRatioCompletedValue = -1;
        activity.step(200);
        assertEquals(1d, lastRatioCompletedValue, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeEllapsedTimeThrowsException() {
        TimedActivity activity = new MockActivity(0, 200);
        activity.step(-1);
    }

    private final class MockActivity extends TimedActivity {

        private MockActivity(final long startTime, final long duration) {
            super(startTime, duration);
        }

        @Override
        public void step(final double ratioCompleted) {
            lastRatioCompletedValue = ratioCompleted;
        }
    }
}
