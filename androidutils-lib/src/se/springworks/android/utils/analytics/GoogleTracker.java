package se.springworks.android.utils.analytics;

import android.app.Activity;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;

public class GoogleTracker implements IAnalyticsTracker {
	

	@Override
	public void trackActivityStart(Activity activity) {
		EasyTracker.getInstance().activityStart(activity);
	}

	@Override
	public void trackActivityStop(Activity activity) {
		EasyTracker.getInstance().activityStop(activity);
	}

	@Override
	public void trackEvent(String category, String action, String label, int value) {
		EasyTracker.getTracker().sendEvent(category, action, label, Long.valueOf(value));
	}

	@Override
	public void init(Context context) {
		EasyTracker.getInstance().setContext(context);
	}

	@Override
	public void trackScreen(String name) {
		EasyTracker.getTracker().sendView(name);
	}

}
