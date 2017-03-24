package placeDetail.subviews;

public class BookingModel implements PlaceDetailSubviewModel {
	private float rating, price;
	private String url, name;

	public BookingModel(float rating, float price, String url, String name) {
		this.rating = rating;
		this.price = price;
		this.url = url;
		this.name = name;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int getType() {
		return PlaceDetailSubviewModel.BOOKING;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
