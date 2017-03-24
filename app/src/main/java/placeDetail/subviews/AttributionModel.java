package placeDetail.subviews;

public class AttributionModel implements PlaceDetailSubviewModel {
	private String title, author, source;

	public AttributionModel(String title, String author, String source) {
		this.title = title;
		this.author = author;
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getSource() {
		return source;
	}

	@Override
	public int getType() {
		return ATTRIBUTION;
	}
}
