package se.springworks.android.utils.map.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class AddressComponent {

	@JsonProperty("long_name")
	private String longName;

	@JsonProperty("short_name")
	private String shortName;

	@JsonProperty("types")
	private ArrayList<String> types;

	public String getLongName() {
		return longName;
	}

	public String getShortName() {
		return shortName;
	}

	public Iterable<String> getTypes() {
		return types;
	}

}
