package se.springworks.android.utils.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import se.springworks.android.utils.application.BaseApplication;

public abstract class BaseActivity extends ActionBarActivity {

  public interface OnActivityResultListener {
    public void onActivityResult(int requestCode, int resultCode, Intent data);
  }


  private OnActivityResultListener activityResultListener = null;

  private static int defaultStartActivityEnterAnimationId = 0;
  private static int defaultStartActivityExitAnimationId = 0;
  private static int defaultFinishActivityEnterAnimationId = 0;
  private static int defaultFinishActivityExitAnimationId = 0;

  private boolean titleBarHidden = false;

  protected void hideTitleBar() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
  }

  public void disableTouchEvents() {
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                         WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
  }

  public void enableTouchEvents() {
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
  }


  @Override
  public void setContentView(int layoutResId) {
    super.setContentView(layoutResId);
    try {
      ActionBar ab = getSupportActionBar();
      if (ab != null && titleBarHidden) {
        ab.hide();
      }
    }
    catch (NullPointerException npe) {
      // do nothing
    }
  }

  /**
   * Called when the activity is first created.
   */
  @Override
  public final void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BaseApplication.getInstance().onActivityCreated(this);

    try {
      createActivity(savedInstanceState);
    }
    catch (Exception e) {
      handleError(e);
    }
  }

  @Override
  public final void onRestart() {
    super.onRestart();
    try {
      restartActivity();
    }
    catch (Exception e) {
      handleError(e);
    }
  }

  @Override
  public final void onStart() {
    super.onStart();
    try {
      startActivity();
    }
    catch (Exception e) {
      handleError(e);
    }
  }

  @Override
  public final void onResume() {
    super.onResume();
    BaseApplication.getInstance().onActivityResumed(this);

    try {
      resumeActivity();
    }
    catch (Exception e) {
      handleError(e);
    }
  }

  @Override
  public final void onPause() {
    super.onPause();
    BaseApplication.getInstance().onActivityPaused(this);

    try {
      pauseActivity();
    }
    catch (Exception e) {
      handleError(e);
    }
  }

  @Override
  public void onStop() {
    super.onStop();

  }

  @Override
  public final void onDestroy() {
    super.onDestroy();
    BaseApplication.getInstance().onActivityDestroyed(this);

    try {
      destroyActivity();
    }
    catch (Exception e) {
      handleError(e);
    }
  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    // there's currently a bug in ActionBarCompat causing an activity
    // with FEATURE_NO_TITLE to crash when the menu button is
    // pressed and onCreateOptionsMenu() is called
    // refer to: http://stackoverflow.com/questions/19275447/oncreateoptionsmenu-causing-error-in
    // -an-activity-with-no-actionbar
    if (keyCode == KeyEvent.KEYCODE_MENU) {
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  /**
   * Suggested implementation in subclasses is to add context information and then rethrow a runtime
   * exception to cause the app to crash and send any information to crash/exception detection
   * system (such as Crittercism)
   *
   * @param e
   */
  protected abstract void handleError(Exception e);

  /**
   * Set the default start activity transitions
   *
   * @param enterId
   * @param exitId
   */
  public static void setDefaultStartActivityTransition(int enterId, int exitId) {
    defaultStartActivityEnterAnimationId = enterId;
    defaultStartActivityExitAnimationId = exitId;
  }

  public static void setDefaultFinishActivityTransition(int enterId, int exitId) {
    defaultFinishActivityEnterAnimationId = enterId;
    defaultFinishActivityExitAnimationId = exitId;
  }

  public final void switchActivity(Class<? extends ActionBarActivity> c) {
    Intent i = new Intent(this, c);
    startActivity(i);
  }

  public final void switchActivity(Class<? extends ActionBarActivity> c, Bundle extras) {
    Intent i = new Intent(this, c);
    if (extras != null) {
      i.putExtras(extras);
    }
    startActivity(i);
  }

  @Override
  public void startActivity(Intent intent) {
    startActivity(intent,
                  defaultStartActivityEnterAnimationId,
                  defaultStartActivityExitAnimationId);
  }

  public void startActivity(Intent intent, int enterAnimationId, int exitAnimationId) {
    super.startActivity(intent);
    overridePendingTransition(enterAnimationId, exitAnimationId);
  }


  public void startActivityForResult(Intent intent,
                                     int requestCode,
                                     OnActivityResultListener listener) {
    this.activityResultListener = listener;
    startActivityForResult(intent, requestCode);
  }

  @Override
  public void startActivityForResult(Intent intent, int requestCode) {
    startActivityForResult(intent,
                           requestCode,
                           defaultStartActivityEnterAnimationId,
                           defaultStartActivityExitAnimationId);
  }

  public void startActivityForResult(Intent intent,
                                     int requestCode,
                                     int enterAnimationId,
                                     int exitAnimationId) {
    if (getParent() != null) {
      getParent().startActivityForResult(intent, requestCode);
      overridePendingTransition(enterAnimationId, exitAnimationId);
    }
    else {
      super.startActivityForResult(intent, requestCode);
      overridePendingTransition(enterAnimationId, exitAnimationId);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (activityResultListener != null) {
      activityResultListener.onActivityResult(requestCode, resultCode, data);
      activityResultListener = null;
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  public void finish() {
    finish(defaultFinishActivityEnterAnimationId, defaultFinishActivityExitAnimationId);
  }

  public void finish(int enterAnimationId, int exitAnimationId) {
    super.finish();
    overridePendingTransition(enterAnimationId, exitAnimationId);
  }

  @Override
  public void finishActivity(int requestCode) {
    finishActivity(requestCode,
                   defaultFinishActivityEnterAnimationId,
                   defaultFinishActivityExitAnimationId);
  }

  public void finishActivity(int requestCode, int enterAnimationId, int exitAnimationId) {
    super.finishActivity(requestCode);
    overridePendingTransition(enterAnimationId, exitAnimationId);
  }

  abstract protected void createActivity(Bundle savedInstanceState);

  protected void destroyActivity() {
    // override if needed
  }

  protected void stopActivity() {
    // override if needed
  }

  protected void restartActivity() {
    // override if needed
  }

  protected void startActivity() {
    // override if needed
  }

  protected void resumeActivity() {
    // override if needed
  }

  protected void pauseActivity() {
    // override if needed
  }
}