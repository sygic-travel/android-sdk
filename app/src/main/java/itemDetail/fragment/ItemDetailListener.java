package itemDetail.fragment;

import com.tripomatic.contentProvider.db.pojo.Reference;

import java.util.List;

public interface ItemDetailListener {
	void onRenderFinished();
	boolean isFull();
	void onUserDataClick();
	void onFodorsClick();
	void onReferencesListClick(List<String> referenceIds);
	void onLeadCreated(Reference reference);
}
