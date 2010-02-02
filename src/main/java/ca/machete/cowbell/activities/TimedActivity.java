package ca.machete.cowbell.activities;

public abstract class TimedActivity implements Activity {

    private final long startTime;

    private final long duration;

    public TimedActivity(final long startTime, final long duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public final long getStartTime() {
        return startTime;
    }

    public final long getDuration() {
        return duration;
    }

    @Override
    public void start() {
        step(0d);
    }

    public void stop() {
        step(1d);
    }

    @Override
    public final boolean step(final long timeEllapsed) {
        if (timeEllapsed < 0)
            throw new IllegalArgumentException("Ellapsed time may not be negative");

        if (timeEllapsed >= duration) {
            step(1d);
        } else {
            step(timeEllapsed / (duration * 1d));
        }

        return timeEllapsed < duration;
    }

    public abstract void step(double ratioCompleted);

}
