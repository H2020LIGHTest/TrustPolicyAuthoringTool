package eu.lightest.tpat.utils;

import java.util.Timer;
import java.util.TimerTask;

public class ReschedulableTimer extends Timer {
  private Runnable mTask;
  private TimerTask mTimerTask;

  public ReschedulableTimer(Runnable runnable) {
    this.mTask = runnable;
  }

  public void schedule(long delay) {
    if (mTimerTask != null)
      mTimerTask.cancel();

    mTimerTask = new TimerTask() {
      @Override
      public void run() {
        mTask.run();
      }
    };
    this.schedule(mTimerTask, delay);
  }

  @Override
  public void cancel() {
    super.cancel();
    mTimerTask.cancel();
  }
}
