package se.springworks.android.utils.application;

import se.springworks.android.utils.guice.LiveModule;
import se.springworks.android.utils.inject.GrapeGuice;
import android.app.Application;

public class BaseApplication extends Application {

	
	public BaseApplication() {
		super();
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		GrapeGuice.addModule(new LiveModule(this));
	}
}
