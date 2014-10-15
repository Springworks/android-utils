package se.springworks.android.utils.application;

import android.app.Application;
import se.springworks.android.utils.activity.BaseActivity;
import se.springworks.android.utils.logging.AndroidLogTarget;
import se.springworks.android.utils.logging.LoggerFactory;

public class BaseApplication extends Application {

  private static BaseApplication instance;
  private BaseActivity current;
  private Class<?> mostRecentActivityClass;
  private int activityCount = 0;

  public BaseApplication() {
    super();
    instance = this;
    AndroidLogTarget target = new AndroidLogTarget();
    target.setConvertDebugToInfo(true);
    LoggerFactory.addTarget(target);
  }

  public static BaseApplication getInstance() {
    return instance;
  }

  public boolean hasCreatedAnyActivities() {
    return activityCount > 0;
//		return mostRecentActivityClass != null;
  }

  public Class<?> getMostRecentActivityClass() {
    return mostRecentActivityClass;
  }

  public BaseActivity getCurrentActivity() {
    return current;
  }

  public void onActivityCreated(BaseActivity a) {
    this.current = a;
    mostRecentActivityClass = a.getClass();
    activityCount++;
  }

  public void onActivityDestroyed(BaseActivity a) {
    if (this.current == a) {
      this.current = null;
    }
    activityCount--;
  }

  public void onActivityPaused(BaseActivity a) {
    if (this.current == a) {
      this.current = null;
    }
  }

  public void onActivityResumed(BaseActivity a) {
    this.current = a;
    mostRecentActivityClass = a.getClass();
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }
}
