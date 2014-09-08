package se.springworks.android.utils.map.geocoding;

public interface IGeoCodingApi {

	void geocode(String address, IGeoCodeCallback callback);

	public interface IGeoCodeCallback {

		public void onSuccess(GeoCodeResults results);

		public void onError(Throwable t, String error);

	}
}
