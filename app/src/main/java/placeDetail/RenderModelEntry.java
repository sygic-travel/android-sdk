package placeDetail;


import placeDetail.subviews.PlaceDetailSubviewModel;

public class RenderModelEntry {
	private String key;
	private PlaceDetailSubviewModel value;

	public RenderModelEntry(String key, PlaceDetailSubviewModel value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public PlaceDetailSubviewModel getValue() {
		return value;
	}

	public void setValue(PlaceDetailSubviewModel value) {
		this.value = value;
	}
}
