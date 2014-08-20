package se.springworks.android.utils.map.directions;


public interface IDirectionsApi {

	public interface OnDirectionsCallback {
		public void onDirections(Directions directions);

		public void onError(Throwable t, String error);
	}

	void directions(String from, String to, TravelMode mode, long departureTimeSeconds, OnDirectionsCallback callback);
}
