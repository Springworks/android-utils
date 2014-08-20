package se.springworks.android.utils.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class FragmentUtil {


	/**
	 * Check if the fragment manager contains a fragment of a specific class, previously
	 * added with one of the show methods
	 *
	 * @param a
	 * @param dialogClass
	 * @return
	 */
	public static boolean contains(Activity a, Class<? extends DialogFragment> dialogClass) {
		FragmentManager fm = a.getFragmentManager();
		return fm.findFragmentByTag(dialogClass.getName()) != null;
	}

	public static void showSingle(Activity a, DialogFragment dialog, boolean addToBackStack, Bundle args) {
		if (contains(a, dialog.getClass())) {
			return;
		}

		if (args != null) {
			dialog.setArguments(args);
		}

		FragmentManager fm = a.getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag(dialog.getClass().getName());
		if (prev != null) {
			ft.remove(prev);
		}
		if (addToBackStack) {
			ft.addToBackStack(null);
		}
		ft.commitAllowingStateLoss();

		dialog.show(fm, dialog.getClass().getName());
	}

	public static void showSingle(Activity a, DialogFragment dialog, boolean addToBackStack) {
		showSingle(a, dialog, addToBackStack, null);
	}

	public static void showSingle(Activity a, DialogFragment dialog) {
		showSingle(a, dialog, true);
	}

	public static void showSingle(Activity a, DialogFragment dialog, Bundle args) {
		showSingle(a, dialog, true, args);
	}

	public static void showSingleNoBack(Activity a, DialogFragment dialog) {
		showSingle(a, dialog, false);
	}


	public static void show(Activity a, DialogFragment dialog) {
		FragmentManager fm = a.getFragmentManager();
		dialog.show(fm, dialog.getClass().getName());
	}


}
