package se.springworks.android.utils.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import se.springworks.android.utils.inject.GrapeGuice;
import se.springworks.android.utils.inject.annotation.InjectLogger;
import se.springworks.android.utils.logging.Logger;

public abstract class BaseFragment extends Fragment {

	@InjectLogger
	private Logger logger;

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		GrapeGuice.getInjector(this).injectMembers(this);
		logger.debug("onCreateView()");
		return createView(inflater, container, savedInstanceState);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		logger.debug("onActivityCreated()");
		GrapeGuice.getInjector(this).injectViews(this);
	}

	/**
	 * Creates the view used by this fragment
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 *
	 * @return
	 */

	protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}

