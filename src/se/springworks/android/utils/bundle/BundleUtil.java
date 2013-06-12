package se.springworks.android.utils.bundle;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

public class BundleUtil {

	public static boolean containsNonEmpty(Bundle b, String key) {
		Object o = b.get(key);
		if(o == null) {
			return false;
		}
		else if(o instanceof String) {
			String s = (String)o;
			return s != null && !s.isEmpty();
		}
		else if(o instanceof byte[]) {
			byte a[] = (byte[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof short[]) {
			short a[] = (short[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof char[]) {
			char a[] = (char[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof int[]) {
			int a[] = (int[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof float[]) {
			float a[] = (float[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof double[]) {
			double a[] = (double[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof String[]) {
			String a[] = (String[])o;
			return a != null && a.length != 0;
		}
		else if(o instanceof ArrayList) {
			ArrayList<?> a = (ArrayList<?>)o;
			return a != null && !a.isEmpty();
		}
		return o != null;
	}
	
	public static int getAsInt(Bundle b, String key) {
		if(!b.containsKey(key)) {
			return 0;
		}
		int value = 0;
		String s = b.getString(key);
		if(s == null) {
			try {
				value = b.getInt(key);
			}
			catch(ClassCastException e) {
				
			}
		}
		else {
			try {
				value = Integer.parseInt(s);
			}
			catch(NumberFormatException e) {
				
			}
		}
		return value;
	}
	
	public static void clearExtras(Intent i) {
		i.putExtras(new Bundle());
	}
	
	public static Bundle copyExtras(Intent i) {
		Bundle copy = new Bundle();
		Bundle original = i.getExtras();
		if(original != null) {
			copy.putAll(original);
		}
		return copy;
		
	}
}
