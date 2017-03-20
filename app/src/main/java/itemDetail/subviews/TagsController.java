package itemDetail.subviews;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripomatic.R;
import com.tripomatic.ui.activity.itemDetail.fragment.ItemDetailFragmentFactories;

import java.util.List;

public class TagsController implements ItemDetailSubview {
	private List<String> tags;
	private TextView tvTags;
	private View rootView;

	public TagsController(ItemDetailSubviewModel tags) {
		this.tags = ((TagsWrapperModel) tags).getTags();
	}

	@Override
	public void render(LinearLayout llContent, Activity activity, ItemDetailFragmentFactories factories) {
		rootView = activity.getLayoutInflater().inflate(R.layout.item_detail_tags_layout, null);

		StringBuilder builder = new StringBuilder();
		builder.append(" ");
		for(int i = 0 ; i < tags.size() ; i++){
			builder.append(tags.get(i).toUpperCase());
			if(i < tags.size() - 1){
				builder.append(" \u2022 ");
			}
		}
		tvTags = (TextView) rootView.findViewById(R.id.tv_tags_item_detail);
		tvTags.append(builder.toString());
		llContent.addView(rootView);
	}
}
