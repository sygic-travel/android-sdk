package itemDetail.subviews;

import java.util.List;

public class TagsWrapperModel implements ItemDetailSubviewModel{
	private List<String> tags;

	public TagsWrapperModel(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public int getType() {
		return TAGS;
	}
}
