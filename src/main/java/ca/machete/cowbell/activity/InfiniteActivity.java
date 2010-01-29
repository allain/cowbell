package ca.machete.cowbell.activity;

public abstract class InfiniteActivity implements Activity {

  private final long startTime;

  public InfiniteActivity() {
    this(System.currentTimeMillis());
  }

  public InfiniteActivity(final long startTime) {
    this.startTime = startTime;
  }

  @Override
  public final long getStartTime() {
    return startTime;
  }

  @Override
  public void start() {

  }

  @Override
  public final boolean step(final long timeEllapsed) {
    step();

    return true;
  }

  public abstract void step();

  @Override
  public final void stop() {

  }

}
