package itemDetail.subviews;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sygic.travel.sdk.model.place.Reference;
import com.sygic.travel.sdkdemo.R;

import java.util.List;

import itemDetail.ItemDetailReferenceUtils;
import itemDetail.fragment.ItemDetailFragmentFactories;

public class MultipleReferencesController extends ReferenceController{
	private List<Reference> otherReferences;
	private TextView tvType, tvAvailable;
	private ImageView ivIcon;
	private View rlLinkRoot;


	public MultipleReferencesController(ItemDetailSubviewModel multipleReferences) {
		super(multipleReferences);
		this.otherReferences = ((MultipleReferencesModel)multipleReferences).getOtherReferences();
	}

	@Override
	public void render(LinearLayout llContent, Activity activity, ItemDetailFragmentFactories factories){
		referenceView = activity.getLayoutInflater().inflate(R.layout.multiple_references_layout, null);

		initUiElements();

		rlLinkRoot = referenceView.findViewById(R.id.rl_link_root);
		tvType = (TextView) referenceView.findViewById(R.id.tv_link_title);
		tvAvailable = (TextView) referenceView.findViewById(R.id.tv_link_count);
		ivIcon = (ImageView) referenceView.findViewById(R.id.sdv_link_icon);

		renderType();

		rlLinkRoot.setOnClickListener(factories.getOnReferenceListClickListener());

		llContent.addView(referenceView);
		renderReference(activity);


	}

	private void renderType(){
		String type = ItemDetailReferenceUtils.normalizeReferenceType(reference.getType());
		switch(type){
			case ItemDetailReferenceUtils.TICKET: {
				show(R.string.item_detail_more_tickets, R.drawable.ic_moretickets);
				break;
			}

			case ItemDetailReferenceUtils.TABLE: {
				show(R.string.item_detail_more_tables, R.drawable.ic_passes);
				break;
			}

			case ItemDetailReferenceUtils.TOUR: {
				show(R.string.item_detail_more_tours, R.drawable.ic_detail_tours);
				break;
			}

			case ItemDetailReferenceUtils.TRANSFER: {
				show(R.string.item_detail_more_transfers, R.drawable.ic_transfers);
				break;
			}

			case ItemDetailReferenceUtils.RENT: {
				show(R.string.item_detail_more_rentals, R.drawable.ic_passes);
				break;
			}

			case ItemDetailReferenceUtils.PARKING: {
				show(R.string.item_detail_more_transfers, R.drawable.ic_transfers);
				break;
			}

			default: {
				break;
			}

		}
		tvAvailable.setVisibility(View.VISIBLE);
		tvAvailable.setText(otherReferences.size() + "");
	}

	private void show(int stringId, int iconId){
		tvType.setText(stringId);
		ivIcon.setImageResource(iconId);
	}
}
