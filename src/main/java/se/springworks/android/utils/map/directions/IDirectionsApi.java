package se.springworks.android.utils.map.directions;


public interface IDirectionsApi {

  void directions(String from,
                  String to,
                  TravelMode mode,
                  long departureTimeSeconds,
                  OnDirectionsCallback callback);

  public interface OnDirectionsCallback {
    public void onDirections(Directions directions);

    public void onError(Throwable t, String error);
  }
}
