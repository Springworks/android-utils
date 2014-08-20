package se.springworks.android.utils.font;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

// https://code.google.com/p/android/issues/detail?id=9904
public class FontCache {

	private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

	public static Typeface get(String name, Context context) {
		Typeface tf = fontCache.get(name);
		if (tf == null) {
			try {
				tf = Typeface.createFromAsset(context.getAssets(), name);
			} catch (Exception e) {
				return null;
			}
			fontCache.put(name, tf);
		}
		return tf;
	}

	public static void remove(String name) {
		fontCache.remove(name);
	}

	public static void clear() {
		fontCache.clear();
	}
}
