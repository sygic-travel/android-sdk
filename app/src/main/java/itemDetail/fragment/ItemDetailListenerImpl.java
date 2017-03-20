package itemDetail.fragment;

import com.tripomatic.SygicTravel;
import com.tripomatic.contentProvider.db.pojo.Reference;
import com.tripomatic.ui.activity.itemDetail.ItemDetailActivity;
import com.tripomatic.utilities.references.ItemDetailReferenceUtils;
import com.tripomatic.utilities.references.ReferenceWrapper;

import java.util.List;

public class ItemDetailListenerImpl implements ItemDetailListener {
	private ItemDetailActivity activity;
	private SygicTravel sygicTravel;

	public ItemDetailListenerImpl(ItemDetailActivity activity, SygicTravel sygicTravel) {
		this.activity = activity;
		this.sygicTravel = sygicTravel;
	}

	@Override
	public void onRenderFinished() {
		activity.reloadItemStatus();
	}

	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void onUserDataClick() {
		activity.startUserDataActivity();
	}

	@Override
	public void onFodorsClick() {
		activity.showFodorsScreen();
	}

	@Override
	public void onReferencesListClick(List<String> referenceIds) {

	}

	@Override
	public void onLeadCreated(Reference reference) {
		ItemDetailReferenceUtils.showReferenceUrl(
			activity,
			sygicTravel,
			new ReferenceWrapper(reference),
			ItemDetailReferenceUtils.DETAIL
		);
	}
}
