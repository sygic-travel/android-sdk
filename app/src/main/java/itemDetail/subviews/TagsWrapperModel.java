package itemDetail.subviews;

import com.sygic.travel.sdk.model.place.TagStats;

import java.util.List;

public class TagsWrapperModel implements ItemDetailSubviewModel{
	private List<TagStats> tags;

	public TagsWrapperModel(List<TagStats> tags) {
		this.tags = tags;
	}

	public List<TagStats> getTags() {
		return tags;
	}

	public void setTags(List<TagStats> tags) {
		this.tags = tags;
	}

	@Override
	public int getType() {
		return TAGS;
	}
}
