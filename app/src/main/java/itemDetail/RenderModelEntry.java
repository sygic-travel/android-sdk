package itemDetail;


import itemDetail.subviews.ItemDetailSubviewModel;

public class RenderModelEntry {
	private String key;
	private ItemDetailSubviewModel value;

	public RenderModelEntry(String key, ItemDetailSubviewModel value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public ItemDetailSubviewModel getValue() {
		return value;
	}

	public void setValue(ItemDetailSubviewModel value) {
		this.value = value;
	}
}
