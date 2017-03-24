package placeDetail.subviews;

public class LatLngModel implements PlaceDetailSubviewModel {
	private boolean drive;
	private double lat, lng;

	public LatLngModel(boolean drive, double lat, double lng) {
		this.drive = drive;
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public int getType() {
		if(drive){
			return LAT_LNG_DRIVE;
		} else {
			return LAT_LNG_WALK;
		}
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
}
