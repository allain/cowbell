package ca.machete.cowbell.activity;

public interface Activity {

  public void stop();

  public void start();

  public boolean step(long timeEllapsed);

  public long getStartTime();
}
