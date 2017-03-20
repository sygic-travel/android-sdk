package itemDetail.subviews;

public class MainInfoModel implements ItemDetailSubviewModel {
	private String title, perex, localTitle;
	private String markerId, guid, perexTranslationProvider;
	private String perexProvider, perexLink;

	private boolean isHotel, estimated;
	private float stars;
	private boolean isTranslated;

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
		this.localTitle = localTitle;
		this.markerId = markerId;
		this.guid = guid;
		this.perexTranslationProvider = perexTranslationProvider;
		this.perexProvider = perexProvider;
		this.perexLink = perexLink;
		this.isHotel = isHotel;
		this.estimated = estimated;
		this.stars = stars;
		this.isTranslated = isTranslated;
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

	public String getLocalTitle() {
		return localTitle;
	}

	public void setLocalTitle(String localTitle) {
		this.localTitle = localTitle;
	}

	public String getMarkerId() {
		return markerId;
	}

	public void setMarkerId(String markerId) {
		this.markerId = markerId;
	}

	public boolean isHotel() {
		return isHotel;
	}

	public void setHotel(boolean hotel) {
		isHotel = hotel;
	}

	public boolean isEstimated() {
		return estimated;
	}

	public void setEstimated(boolean estimated) {
		this.estimated = estimated;
	}

	public float getStars() {
		return stars;
	}

	public void setStars(float stars) {
		this.stars = stars;
	}

	public boolean isTranslated() {
		return isTranslated;
	}

	public void setTranslated(boolean translated) {
		isTranslated = translated;
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
