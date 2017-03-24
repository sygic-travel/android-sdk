package placeDetail.subviews;

public class BasicExpandableElement implements PlaceDetailSubviewModel {
	private String title, expandableText;
	private int iconId;

	public BasicExpandableElement(String title, String expandableText, int iconId) {
		this.title = title;
		this.expandableText = expandableText;
		this.iconId = iconId;
	}

	@Override
	public int getType() {
		return BASIC_EXPANDABLE;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExpandableText() {
		return expandableText;
	}

	public void setExpandableText(String expandableText) {
		this.expandableText = expandableText;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}
}
