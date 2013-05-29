package se.springworks.android.utils.view;

import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LinkifyUtil {

	
	public static void linkifyAllTextViews(ViewGroup vg, int mask) {
		int childCount = vg.getChildCount();
		for(int i = 0; i < childCount; i++) {
			View v = vg.getChildAt(i);
			if(v instanceof TextView) {
				TextView tv = (TextView)v;
				Linkify.addLinks(tv, mask);
			}
			
			if(v instanceof ViewGroup) {
				linkifyAllTextViews((ViewGroup)v, mask);
			}
		}
	}
	
	public static void linkifyAllTextViews(View v, int mask) {
		if(v instanceof TextView) {
			TextView tv = (TextView)v;
			Linkify.addLinks(tv, mask);
		}
		if(v instanceof ViewGroup) {
			linkifyAllTextViews((ViewGroup)v, mask);
		}
	}
	
	public static SpannableString linkifyAll(String text) {
		return linkify(text, Linkify.ALL);
	}
	
	public static SpannableString linkify(String text, int mask) {
		SpannableString s = new SpannableString(text);
		Linkify.addLinks(s, mask);
		return s;
	}
}
