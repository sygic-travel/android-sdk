package placeDetail.subviews;

import android.app.Activity;
import android.widget.LinearLayout;

import placeDetail.fragment.PlaceDetailFragmentFactories;


public interface PlaceDetailSubview {
	public void render(LinearLayout llContent, Activity activity, PlaceDetailFragmentFactories factories);
}
