package placeDetail.subviews;

public class MainInfoModel implements PlaceDetailSubviewModel {
	private String title, perex;
	private String markerId, guid, perexTranslationProvider;
	private String perexProvider, perexLink;


	public MainInfoModel(
		String title,
		String perex,
		String localTitle,
		String markerId,
		String guid,
		String perexTranslationProvider,
		String perexProvider,
		String perexLink,
		boolean isHotel,
		boolean estimated,
		float stars,
		boolean isTranslated
	) {
		this.title = title;
		this.perex = perex;
		this.markerId = markerId;
		this.guid = guid;
		this.perexTranslationProvider = perexTranslationProvider;
		this.perexProvider = perexProvider;
		this.perexLink = perexLink;
	}

	@Override
	public int getType() {
		return MAIN_LAYOUT;
	}

	public String getPerexProvider() {
		return perexProvider;
	}

	public void setPerexProvider(String perexProvider) {
		this.perexProvider = perexProvider;
	}

	public String getPerexLink() {
		return perexLink;
	}

	public void setPerexLink(String perexLink) {
		this.perexLink = perexLink;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPerex() {
		return perex;
	}

	public void setPerex(String perex) {
		this.perex = perex;
	}

	public String getMarkerId() {
		return markerId;
	}

	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getPerexTranslationProvider() {
		return perexTranslationProvider;
	}

	public void setPerexTranslationProvider(String perexTranslationProvider) {
		this.perexTranslationProvider = perexTranslationProvider;
	}
}
