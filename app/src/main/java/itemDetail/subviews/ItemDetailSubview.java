package itemDetail.subviews;

import android.app.Activity;
import android.widget.LinearLayout;

import itemDetail.fragment.ItemDetailFragmentFactories;


public interface ItemDetailSubview {
	public void render(LinearLayout llContent, Activity activity, ItemDetailFragmentFactories factories);
}
