package itemDetail.fragment;


import com.sygic.travel.sdk.model.place.Reference;

import java.util.List;

public interface ItemDetailListener {
	void onRenderFinished();
	boolean isFull();
	void onUserDataClick();
	void onFodorsClick();
	void onReferencesListClick(List<String> referenceIds);
	void onLeadCreated(Reference reference);
}
