package placeDetail;


import com.sygic.travel.sdk.model.place.Reference;

public class ReferenceWrapper {
	private String type;
	private String supplier;
	private String title;
	private int id;
	private float price;
	private String url;

	public ReferenceWrapper(Reference reference){
		type = reference.getType();
		supplier = reference.getSupplier();
		title = reference.getTitle();
		//id = reference.getId();
		price = reference.getPrice();
		url = reference.getUrl();
	}

	public String getType() {
		return type;
	}

	public String getSupplier() {
		return supplier;
	}

	public String getTitle() {
		return title;
	}

	public int getId() {
		return id;
	}

	public float getPrice() {
		return price;
	}

	public String getUrl() {
		return url;
	}
}
