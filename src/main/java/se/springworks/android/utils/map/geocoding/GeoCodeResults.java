package se.springworks.android.utils.map.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GeoCodeResults {

	/**
	 * an array of geocoded address information and geometry information
	 */
	@JsonProperty("results")
	private ArrayList<GeoCodeResult> results = new ArrayList<GeoCodeResult>();

	@JsonProperty("status")
	private String status;

	public List<GeoCodeResult> getResults() {
		return results;
	}

	public int size() {
		return results.size();
	}

	public String getStatus() {
		return status;
	}

	public void add(GeoCodeResult result) {
		results.add(result);
	}

	public GeoCodeResult get(int index) {
		return results.get(index);
	}

	@Override
	public String toString() {
		return super.toString() + " " + status;
	}
}
