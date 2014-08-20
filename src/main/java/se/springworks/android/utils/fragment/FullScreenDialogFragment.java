package se.springworks.android.utils.fragment;

import android.os.Bundle;

import se.springworks.android.utils.R;

public abstract class FullScreenDialogFragment extends BaseDialogFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_FRAME, R.style.dialognoborder);
	}
}
