package itemDetail.subviews;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sygic.travel.sdkdemo.R;

import itemDetail.fragment.ItemDetailFragmentFactories;
import itemDetail.toBeDeleted.Utils;


public class AttributionLayoutController implements ItemDetailSubview {
	private AttributionModel attributionModel;
	private View rootView;
	private TextView tvAttribution, tvAuthor;

	public AttributionLayoutController(ItemDetailSubviewModel model){
		attributionModel = (AttributionModel) model;
	}

	@Override
	public void render(LinearLayout llContent, Activity activity, ItemDetailFragmentFactories factories) {
		rootView = activity.getLayoutInflater().inflate(R.layout.attribution_layout, null);
		tvAttribution = (TextView) rootView.findViewById(R.id.tv_attribution);
		tvAuthor = (TextView) rootView.findViewById(R.id.tv_author);

		tvAttribution.setText(
			Utils.fromHtml(
				activity.getString(R.string.detail_photo_attribution) +
				attributionModel.getTitle()
			)
		);

		tvAttribution.setMovementMethod(LinkMovementMethod.getInstance());

		tvAuthor.setMovementMethod(LinkMovementMethod.getInstance());

		tvAuthor.setText(
			Utils.fromHtml(
				activity.getString(R.string.by) +
					" " +
					attributionModel.getAuthor() +
					" @ " +
					attributionModel.getSource()
			)
		);

		llContent.addView(rootView);
	}
}
