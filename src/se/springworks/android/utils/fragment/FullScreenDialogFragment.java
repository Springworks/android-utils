package se.springworks.android.utils.fragment;

import se.springworks.android.utils.R;
import se.springworks.android.utils.inject.GrapeGuice;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.logging.Logger;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FullScreenDialogFragment extends DialogFragment {


	@InjectLogger
	private Logger logger;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_FRAME, R.style.dialognoborder);
	}
	
	
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		GrapeGuice.getInjector(this).injectMembers(this);
		logger.debug("onCreateView()");
		return createView(inflater, container);
	}
	
	@Override
	public final void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logger.debug("onActivityCreated()");
		GrapeGuice.getInjector(this).injectViews(this);
		fragmentReadyToUse(savedInstanceState);
	}
	
	abstract protected void fragmentReadyToUse(Bundle savedInstanceState);

	public abstract View createView(LayoutInflater inflater, ViewGroup container);
}
