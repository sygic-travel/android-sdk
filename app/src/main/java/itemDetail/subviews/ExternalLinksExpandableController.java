package itemDetail.subviews;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripomatic.R;
import com.tripomatic.contentProvider.db.pojo.Reference;
import com.tripomatic.ui.activity.itemDetail.fragment.ItemDetailFragmentFactories;
import com.tripomatic.utilities.references.ItemDetailReferenceUtils;
import com.tripomatic.utilities.references.ReferenceWrapper;

public class ExternalLinksExpandableController extends ExpandableElementController {
	public Runnable onLeadCreatedRunnable;

	public ExternalLinksExpandableController(ItemDetailSubviewModel dependencies) {
		super(dependencies);
	}

	@Override
	public void render(LinearLayout llContent, final Activity activity, ItemDetailFragmentFactories factories){
		initUiElements(activity);

		MultipleReferencesModel model = (MultipleReferencesModel) dependencies;


		tvTitle.setText(activity.getString(R.string.item_detail_external_link));
		ivIcon.setImageResource(R.drawable.ic_links);
		expandedText.setVisibility(View.GONE);

		for(final Reference reference : model.getOtherReferences()){
			TextView tvReference = (TextView) activity.getLayoutInflater().inflate(R.layout.external_link_text_view, null);
			SpannableString content = new SpannableString(reference.getTitle());
			content.setSpan(new UnderlineSpan(), 0, reference.getTitle().length(), 0);
			tvReference.setText(content);
			tvReference.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					ItemDetailReferenceUtils.showReferenceUrl(
						activity,
						new ReferenceWrapper(reference),
						ItemDetailReferenceUtils.DETAIL
					);
				}
			});

			expandableView.addView(tvReference);
		}

		expandableView.setVisibility(View.GONE);

		rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openClose();
			}
		});

		llContent.addView(rootView);
	}
}
