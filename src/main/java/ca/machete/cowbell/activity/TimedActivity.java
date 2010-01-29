package ca.machete.cowbell.activity;


public abstract class TimedActivity implements Activity {
    private final long startTime;
    private final long duration;
    
    
    public TimedActivity(long startTime, long duration) {
        this.startTime = startTime;
        this.duration = duration;
    }
    
    public final long getStartTime() {
        return startTime;
    }
    
    @Override
    public void start() {
       step(0d);   
    }
    
    public void stop() {
        step(1d);   
      }

    @Override
    public final boolean step(long timeEllapsed) {
        assert timeEllapsed <= duration;
        assert timeEllapsed >= 0;
        
        if (timeEllapsed >= duration) {
            step(1d);
        } else {
            step(timeEllapsed/(duration*1d));
        }
        
        return timeEllapsed < duration;
    }
    
    public abstract void step(double ratioCompleted);
}
